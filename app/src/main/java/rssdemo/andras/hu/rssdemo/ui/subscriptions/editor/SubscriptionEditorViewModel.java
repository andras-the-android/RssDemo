package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor;


import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;

public class SubscriptionEditorViewModel {

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionEditorView view;
    private boolean isEditMode;
    private String originalName;

    public SubscriptionEditorViewModel(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void setView(SubscriptionEditorView view, String originalName) {
        this.view = view;
        this.isEditMode = originalName != null;
        this.originalName = originalName == null ? "" : originalName;
    }

    public void onSaveClick() {
        Subscription subscription = view.getSubscrition();
        if (validateName(subscription.getName()) & validateUrl(subscription.getUrl())) {
            if (isEditMode) {
                subscriptionRepository.update(originalName, subscription);
            } else {
                subscriptionRepository.create(subscription);
            }
            view.closeEditor();
        }
    }

    private boolean validateName(String name) {
        if (name.length() == 0) {
            view.raiseErrorOnName(R.string.error_name_mandatory);
            return false;
        }
        if (!name.equals(originalName) && subscriptionRepository.isNameExisting(name)) {
            view.raiseErrorOnName(R.string.error_name_must_be_unique);
            return false;
        }
        return true;
    }

    private boolean validateUrl(String url) {
        if (url.length() == 0) {
            view.raiseErrorOnUrl(R.string.error_url_mandatory);
            return false;
        }
        return true;
    }

    public void onCancelClick() {
        view.closeEditor();
    }
}
