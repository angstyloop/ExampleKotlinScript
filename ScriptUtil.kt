package kotlinscriptresources

import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility

// read args from an array (e.g. the one available in the scope of .kts file) into a generic object
inline fun <reified T: Any> readArgsInto(args: Array<String>, obj: T): T {
    if (args.size < 2) error("Not enough arguments.") //usage()
    var i = 0
    while (i < args.size) {
        if (! args[i].startsWith("--")) error("Incorrect syntax for arguments.")
        args[i].substring(2).let { arg ->
            if (T::class.memberProperties.map{ prop -> prop.name }.contains(arg)) {
                ++i
                if (i < args.size) {
                    setValue<T>(obj, arg, args[i])
                } else {
                    error("Final argument name in command line argument list does not have a matching value.")
                }
            } else {
                error("Property $arg does not exist on object of class ${T::class.toString()}")
            }
            ++i
        }
    }
    return obj
}

fun script(args: Array<String>, block: Args.() -> Unit) = with(readArgsInto<Args>(args, Args())) { block(this) }
