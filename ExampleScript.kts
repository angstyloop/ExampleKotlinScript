import kotlinscriptresources.Args
import kotlinscriptresources.script
import kotlinscriptresources.usage

script(this.args, this.javaClass.name) {
    println(fileName)
    println(exportName)
    println(value)
    println(quoteType)
}
