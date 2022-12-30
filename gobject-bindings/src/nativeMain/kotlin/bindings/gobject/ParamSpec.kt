package bindings.gobject

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gobject.*

open class ParamSpec : Object {
    val gParamSpecPointer get() = gPointer.asTypedPointer<GParamSpec>()

    constructor(pointer: CPointer<*>) : super(pointer)

    val name: String get() = g_param_spec_get_name(gParamSpecPointer)!!.toKString()
    val blurb: String? get() = g_param_spec_get_blurb(gParamSpecPointer)?.toKString()
    val nick: String? get() = g_param_spec_get_nick(gParamSpecPointer)?.toKString()

}