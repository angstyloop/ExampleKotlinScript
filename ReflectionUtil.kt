package kotlinscriptresources

import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility

// get the value of the property by reflecting on its name
inline fun <reified T: Any> getValue(instance: T, propertyName: String): Any? =
    T::class.memberProperties.find{ it.name == propertyName }
        ?.get(instance)
        ?:error("No argument named $propertyName exists.")

//fun getValue(instance: Args, propertyName: String): Any? =

// set the value of the property by reflecting on its name
inline fun <reified T: Any> setValue(instance: T, propertyName: String, propertyValue: Any?) {
    T::class.memberProperties
        // The docs say to futureproof against prevent runtime exceptions on private members by filtering before reading.
        .filter{ it.visibility == KVisibility.PUBLIC }
        .filterIsInstance<KMutableProperty<*>>()
        // ::find seems like it would need to read the KProperty ::it
        .find{ it.name == propertyName }
        ?.setter?.call(instance, propertyValue)
        ?:error("No argument named $propertyName exists, or no setter exists on that property.")
}
