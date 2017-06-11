package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import rssdemo.andras.hu.rssdemo.data.Subscription
import rssdemo.andras.hu.rssdemo.databinding.ViewSubscriptionEditorBinding
import rssdemo.andras.hu.rssdemo.di.Injector


class SubscriptionEditorDialogFragment : DialogFragment(), SubscriptionEditorView {

    lateinit var viewModel: SubscriptionEditorViewModel

    private lateinit var nameValidatorHelper: TextInputLayoutValidationHelper
    private lateinit var urlValidatorHelper: TextInputLayoutValidationHelper
    private lateinit var binding: ViewSubscriptionEditorBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewSubscriptionEditorBinding.inflate(inflater, container, false)

        val subscription = Subscription()
        subscription.name = arguments.getString(KEY_NAME, "")
        subscription.url = arguments.getString(KEY_URL, "")

        if (subscription.url.isEmpty()) {
            subscription.url = "http://"
        }

        viewModel.setView(this, subscription.name)
        binding.subscription = subscription
        binding.handler = viewModel
        nameValidatorHelper = TextInputLayoutValidationHelper(context, binding.tilName)
        urlValidatorHelper = TextInputLayoutValidationHelper(context, binding.tilUrl)
        return binding.root
    }

    override fun raiseErrorOnName(@StringRes messageResId: Int) {
        nameValidatorHelper.raiseError(messageResId)
    }

    override fun raiseErrorOnUrl(@StringRes messageResId: Int) {
        urlValidatorHelper.raiseError(messageResId)
    }

    override fun closeEditor() {
        dialog.dismiss()
    }

    override fun getSubscription(): Subscription {
        return Subscription(binding.name.text.toString().trim { it <= ' ' }, binding.url.text.toString().trim { it <= ' ' })
    }

    companion object {

        private val KEY_NAME = "name"
        private val KEY_URL = "url"

        @JvmOverloads fun create(subscription: Subscription? = null): SubscriptionEditorDialogFragment {
            val fragment = SubscriptionEditorDialogFragment()
            Injector.inject(fragment)
            val args = Bundle()
            if (subscription != null) {
                args.putString(KEY_NAME, subscription.name)
                args.putString(KEY_URL, subscription.url)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
