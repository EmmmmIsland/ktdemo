package com.demo.toolkit.ext

import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


inline fun <T> T?.nullOr(value : T) : T = if (this != null) this else value

fun <T> T.transform(action : (T)->T) : T =  action(this)
fun <T> T.transformWithSwitch(action2 : (T, Boolean)->T, switch: Boolean) : T =  action2(this, switch)

fun Fragment.getDrawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(this.resources, resId, this.context?.theme)

fun <T> Any.asInstanceOf(clazz: Class<T>)= if(clazz.isInstance(this)){this as T}else{null}

class LazyReadWriteProperty<T, V>(private val initializer : ()-> ReadWriteProperty<T, V>)
    : ReadWriteProperty<T, V> {
    private object EMPTY
    private var mValue: Any = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (mValue == EMPTY) {
            mValue = initializer()
        }
        return (mValue as ReadWriteProperty<T, V>).getValue(thisRef, property)
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        if (mValue == EMPTY) {
            mValue = initializer()
        }
        (mValue as ReadWriteProperty<T, V>).setValue(thisRef, property, value)
    }
}

@Deprecated("Reduction", replaceWith = ReplaceWith("this == true"))
inline fun Boolean?.nullAsFalse() : Boolean = this ?: false

fun <T> simpleLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)