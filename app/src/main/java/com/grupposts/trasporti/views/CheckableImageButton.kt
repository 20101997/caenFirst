package com.grupposts.trasporti.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.ClassLoaderCreator
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityEventCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.view.AbsSavedState

class CheckableImageButton @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.imageButtonStyle
) : AppCompatImageButton(context!!, attrs, defStyleAttr), Checkable {

    private var checked = false
    private var checkable = true
    /** Returns whether the image button is pressable.  */
    /** Sets image button to be pressable or not.  */
    var isPressable = true

    private var listener: ((Boolean) -> Unit)? = null

    init {
        val styledAttrs = context?.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.checked))
        val checkedAttr = styledAttrs?.getBoolean(0, false)
        checked = checkedAttr ?: false
        styledAttrs?.recycle()

        setOnClickListener {
            toggle()
        }
    }

    fun setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }

    override fun setChecked(checked: Boolean) {
        if (checkable && this.checked != checked) {
            this.checked = checked
            refreshDrawableState()
            sendAccessibilityEvent(AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED)
        }
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun toggle() {
        isChecked = !checked
        listener?.invoke(isChecked)
    }

    override fun setPressed(pressed: Boolean) {
        if (isPressable) {
            super.setPressed(pressed)
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return if (checked) {
            mergeDrawableStates(
                super.onCreateDrawableState(extraSpace + DRAWABLE_STATE_CHECKED.size),
                DRAWABLE_STATE_CHECKED
            )
        } else {
            super.onCreateDrawableState(extraSpace)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.checked = checked
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val savedState = state
        super.onRestoreInstanceState(savedState.superState)
        isChecked = savedState.checked
    }

    /** Sets image button to be checkable or not.  */
    fun setCheckable(checkable: Boolean) {
        if (this.checkable != checkable) {
            this.checkable = checkable
            sendAccessibilityEvent(AccessibilityEventCompat.CONTENT_CHANGE_TYPE_UNDEFINED)
        }
    }

    /** Returns whether the image button is checkable.  */
    fun isCheckable(): Boolean {
        return checkable
    }

    internal class SavedState : AbsSavedState {
        var checked = false

        constructor(superState: Parcelable?) : super(superState!!) {}
        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            readFromParcel(source)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (checked) 1 else 0)
        }

        private fun readFromParcel(`in`: Parcel) {
            checked = `in`.readInt() == 1
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : ClassLoaderCreator<SavedState> {
                override fun createFromParcel(`in`: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(`in`, loader)
                }

                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`, null)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        private val DRAWABLE_STATE_CHECKED = intArrayOf(android.R.attr.state_checked)
    }

    init {
        ViewCompat.setAccessibilityDelegate(
            this,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityEvent(host: View, event: AccessibilityEvent) {
                    super.onInitializeAccessibilityEvent(host, event)
                    event.isChecked = isChecked
                }

                override fun onInitializeAccessibilityNodeInfo(
                    host: View, info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.isCheckable = isCheckable()
                    info.isChecked = isChecked
                }
            })
    }
}