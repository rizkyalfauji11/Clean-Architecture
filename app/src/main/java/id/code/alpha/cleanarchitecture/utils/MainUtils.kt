package id.code.alpha.cleanarchitecture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun FragmentManager.doTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


fun AppCompatActivity.replaceFragment(
    frameId: Int,
    fragment: Class<out Fragment>,
    argument: Bundle?
) {
    supportFragmentManager.doTransaction {
        replace(frameId, fragment, argument, null)
        addToBackStack(null)
    }
}

fun Activity.startActivity(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Class<out Fragment>): Int? {
    supportFragmentManager.doTransaction {
        replace(frameId, fragment, null, null)
        addToBackStack(null)
    }
    return null
}

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment): Float? {
    supportFragmentManager.doTransaction {
        replace(frameId, fragment, null)
        addToBackStack(null)
    }
    return null
}

fun <T> Fragment.viewLifecycleLazy(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            this@viewLifecycleLazy
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycleLazy, { newLifecycleOwner ->
                    viewLifecycleOwner
                        ?.lifecycle
                        ?.removeObserver(this)

                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            return this.binding ?: initialise().also {
                this.binding = it
            }
        }
    }

fun Fragment.horizontalLayoutManager(): LinearLayoutManager = LinearLayoutManager(
    context,
    LinearLayoutManager.HORIZONTAL,
    false
)

fun Fragment.verticalLayoutManager(): LinearLayoutManager = LinearLayoutManager(
    context,
    LinearLayoutManager.VERTICAL,
    false
)

fun Fragment.gridLayoutManager(count: Int): GridLayoutManager = GridLayoutManager(
    context,
    count
)

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

fun String?.getImageIdByName(context: Context?): Int? {
    return context?.resources?.getIdentifier(this, "drawable", context.packageName)
}

fun Context?.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visible(status: Boolean) {
    visibility = if (status) View.VISIBLE else View.GONE
}

fun View.enabled(status: Boolean) {
    isEnabled = status
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}