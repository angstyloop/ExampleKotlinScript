package kotlinscriptresources

import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility

// read args from an array (e.g. the one available in the scope of .kts file) into a generic object
inline fun <reified T: Any> readArgsInto(args: Array<String>, obj: T, scriptName: String): T {
    if (args.size < 2) error("Not enough arguments.\n\n${usage(scriptName)}") //usage()
    var i = 0
    while (i < args.size) {
        if (! args[i].startsWith("--")) error("Incorrect syntax for arguments.\n\n${usage(scriptName)}")
        args[i].substring(2).let { arg ->
            if (T::class.memberProperties.map{ prop -> prop.name }.contains(arg)) {
                ++i
                if (i < args.size) {
                    try {setValue<T>(obj, arg, args[i])} catch(e: Exception) {error("${e.message}\n\n${usage(scriptName)}")}
                } else {
                    error("Final argument name in command line argument list does not have a matching value.\n\n${usage(scriptName)}")
                }
            } else {
                error("Property $arg does not exist on object of class ${T::class.toString()}. \n\n${usage(scriptName)}")
            }
            ++i
        }
    }
    return obj
}

fun script(args: Array<String>, scriptName: String, block: Args.() -> Any?): Any? =
    with(readArgsInto<Args>(args, Args(), scriptName)) { block(this) }

fun usage(scriptName: String): String {
    val sb: StringBuilder = StringBuilder("Usage: ./$scriptName")
    Args::class.memberProperties.map{it.name}.forEach{propName ->
        sb.append(" --$propName <${propName.capitalize()}>")
    }
    return sb.toString()
}