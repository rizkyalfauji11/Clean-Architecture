package id.code.alpha.domain.utils

interface ModelParser<O> {
    fun convert(): O
}

fun <T> List<ModelParser<T>>.mapList(): List<T> {
    val list = mutableListOf<T>()
    this.forEach {
        list.add(it.convert())
    }
    return list
}