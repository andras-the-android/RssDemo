package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor;


import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;

public class SubscriptionEditorViewModel {

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionEditorView view;
    private boolean isEditMode;
    private String originalName;

    public SubscriptionEditorViewModel(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void setView(SubscriptionEditorView view, boolean isEditMode, String originalName) {
        this.view = view;
        this.isEditMode = isEditMode;
        this.originalName = originalName;
    }

    public void onSaveClick() {

    }

    public void onCancelClick() {
        view.closeEditor();
    }
}
