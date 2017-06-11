package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor


import rssdemo.andras.hu.rssdemo.R
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository

class SubscriptionEditorViewModel(private val subscriptionRepository: SubscriptionRepository) {
    private var isEditMode: Boolean = false
    private lateinit var view: SubscriptionEditorView
    private lateinit var originalName: String

    internal fun setView(view: SubscriptionEditorView, originalName: String) {
        this.view = view
        this.isEditMode = originalName.isNotEmpty()
        this.originalName = originalName
    }

    fun onSaveClick() {
        val subscription = view.getSubscription()
        if (validateName(subscription.name) and validateUrl(subscription.url)) {
            if (isEditMode) {
                subscriptionRepository.update(originalName, subscription)
            } else {
                subscriptionRepository.create(subscription)
            }
            view.closeEditor()
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            view.raiseErrorOnName(R.string.error_name_mandatory)
            return false
        }
        if (name != originalName && subscriptionRepository.isNameExisting(name)) {
            view.raiseErrorOnName(R.string.error_name_must_be_unique)
            return false
        }
        return true
    }

    private fun validateUrl(url: String): Boolean {
        if (url.isEmpty()) {
            view.raiseErrorOnUrl(R.string.error_url_mandatory)
            return false
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            view.raiseErrorOnUrl(R.string.error_not_url)
            return false
        }
        return true
    }

    fun onCancelClick() {
        view.closeEditor()
    }
}
