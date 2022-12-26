package bindings.util

import kotlinx.cinterop.*

fun List<String>.toCStringList(scope: MemScope): CArrayPointer<CPointerVarOf<CPointer<ByteVar>>> = with(scope) {
    return allocArrayOf(this@toCStringList.map { it?.cstr?.getPointer(this) } + null)
}

fun CArrayPointer<CPointerVarOf<CPointer<ByteVar>>>.toStringList(): List<String> {
    var i = 0
    val list = mutableListOf<String>()
    while (this[i] != null) {
        list.add(this[i]!!.toKString())
        i++
    }
    return list
}
