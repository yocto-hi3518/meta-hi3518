# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "https://github.com/hi35xx/hi35xx-buildroot/raw/master/package/himpp/himpp-hi3518v100/hi3518v100-mpp2-1.0.B.0.tgz \
           file://0001-Makefile-fixes.patch \
           file://clkcfg_hi3518.sh \
           file://load3518.sh \
           file://lowpower.sh \
           file://pinmux_hi3518.sh \
           file://sysctl_hi3518.sh \
           file://load_himpp \
           file://0001-testing.patch \
           "

#SRC_URI +=  "file://0002-SC1135-support.patch "

SRC_URI[md5sum] = "50f84546addceac4e4fc8163b06ee995"
SRC_URI[sha256sum] = "45b363b53ead77b673556e5d840c6bb2f603f203a8fe6a5e833b909898f32bbe"

inherit module

#EXTRA_OEMAKE_append_task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
# Unable to find means of passing kernel path into install makefile - if kernel path is hardcoded you will need to patch the makefile. Note that the variable KERNEL_SRC will be passed in as the kernel source path.

S = "${WORKDIR}"

PARALLEL_MAKE = ""

EXTRA_OEMAKE += "\
    ARCH=${ARCH} \
    HIARCH=hi3518 \
    LIBC=${TCLIBC} \
    CROSS=${TARGET_PREFIX} \
    CROSS_COMPILE=${TARGET_PREFIX} \
    LINUX_ROOT=${STAGING_KERNEL_DIR} \
    MPP_PATH=${S}/mpp2 \
    TARGET_CFLAGS="--sysroot=${STAGING_DIR_TARGET} -Wno-parentheses " \
    TARGET_ARFLAGS_SO="--sysroot=${STAGING_DIR_TARGET}" \
"

HIMMP_LIB_DIR = "mpp2/component/isp2/lib"
HIMMP_SENSOR_DIR = "mpp2/component/isp2/sensor"
HIMMP_EXT_DIR = "mpp2/extdrv"
HIMPP_SAMPLE_DIR = "mpp2/sample"

himpp_make() {
   export LDFLAGS=
   (cd ${S}/$1/$2 && oe_runmake)
}

himpp_make_sensor() {
    SENSOR_TYPE=$1 himpp_make ${HIMMP_SENSOR_DIR} $1
}

himpp_make_ext() {
    himpp_make ${HIMMP_EXT_DIR} $1
}

himpp_make_sample () {
    himpp_make ${HIMPP_SAMPLE_DIR} $1
}

do_compile() {
    himpp_make_sensor aptina_9m034
    himpp_make_sensor aptina_ar0130
    himpp_make_sensor aptina_ar0140
    himpp_make_sensor aptina_ar0330
    himpp_make_sensor aptina_mt9p006
    himpp_make_sensor himax_1375
    himpp_make_sensor ov_9712
    himpp_make_sensor ov_9712+
    himpp_make_sensor pana34031
    himpp_make_sensor pana34041
    himpp_make_sensor pixelplus_3100k
    himpp_make_sensor soi_h22
    himpp_make_sensor sony_icx692
    himpp_make_sensor sony_imx104
    himpp_make_sensor sony_imx122
    himpp_make_sensor sony_imx138
    himpp_make_sensor sony_imx225
    himpp_make_sensor sony_imx236
    himpp_make_sensor smartsens_sc1135


    himpp_make_ext gpio-i2c
    himpp_make_ext gpio-i2c-ex
    himpp_make_ext hi_i2c
    himpp_make_ext pwm
    himpp_make_ext ssp-ad9020
    himpp_make_ext ssp-pana
    himpp_make_ext ssp-sony
    himpp_make_ext tw2865

    himpp_make_sample audio
    #himpp_make_sample hifb
    #himpp_make_sample iq
    himpp_make_sample ive
    #himpp_make_sample readme
    #himpp_make_sample region
    #himpp_make_sample tde
    #himpp_make_sample vda
    himpp_make_sample venc
    #himpp_make_sample vio

   cd ${S}/mpp2/lib/
   for f in *.a; do 
     $CC -shared -fPIC -o ${f%.a}.so -Wl,--whole-archive $f -Wl,--no-whole-archive
   done

}

kmoddir = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/himpp"

INSANE_SKIP_${PN} += "ldflags file-rdeps"
INSANE_SKIP_${PN}-dev += "dev-elf"


FILES_${PN} += " ${kmoddir}/*.sh ${bindir}/load_himpp /himpp_samples"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_install() {
   install -d ${D}${kmoddir}
   install -d ${D}${libdir}
   install -m 0755 ${S}/*.sh ${D}${kmoddir}/
   install -m 0755 ${S}/mpp2/ko/*.ko ${D}${kmoddir}
   install -m 0755 ${S}/mpp2/lib/*.so ${D}${libdir}
   install -m 0755 ${S}/${HIMMP_EXT_DIR}/*/*.ko ${D}/${kmoddir}
   install -m 0755 ${S}/${HIMMP_LIB_DIR}/*.a ${D}${libdir}
   install -m 0755 ${S}/${HIMMP_LIB_DIR}/*.so ${D}${libdir}
   
   install -d ${D}${bindir}
   install -m 0755 ${S}/load_himpp ${D}${bindir}/load_himpp

   install -d ${D}/himpp_samples
   install -m 0755 ${S}/${HIMPP_SAMPLE_DIR}/venc/sample_venc ${D}/himpp_samples/sample_venc

   install -d  ${D}${includedir}/hi3518v100mpp
   cp -a ${S}/mpp2/include/* ${D}${includedir}/hi3518v100mpp
}
