package com.spentwell.utils

import androidx.databinding.BindingAdapter
import club.cred.synth.views.SynthButton
import com.spentwell.R

@BindingAdapter("app:isButtonActive")
fun SynthButton.activate(isButtonActive: Boolean) {
    isEnabled = isButtonActive
    isClickable = isButtonActive
    flatButtonColor =
        if (isButtonActive) context.getColor(R.color.colorButtonEnabled)
        else context.getColor(R.color.colorButtonDisabled)
}