package com.mvvm.kien2111.mvvmapplication.databinding;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAdminHomeBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.imv_evaluate_1, 2);
        sViewsWithIds.put(R.id.imv_evaluate_2, 3);
        sViewsWithIds.put(R.id.imv_evaluate_3, 4);
        sViewsWithIds.put(R.id.imv_evaluate_4, 5);
        sViewsWithIds.put(R.id.imv_evaluate_5, 6);
    }
    // views
    @NonNull
    public final android.widget.ImageView imvEvaluate1;
    @NonNull
    public final android.widget.ImageView imvEvaluate2;
    @NonNull
    public final android.widget.ImageView imvEvaluate3;
    @NonNull
    public final android.widget.ImageView imvEvaluate4;
    @NonNull
    public final android.widget.ImageView imvEvaluate5;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    // variables
    @Nullable
    private com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel mVm;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAdminHomeBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 1);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.imvEvaluate1 = (android.widget.ImageView) bindings[2];
        this.imvEvaluate2 = (android.widget.ImageView) bindings[3];
        this.imvEvaluate3 = (android.widget.ImageView) bindings[4];
        this.imvEvaluate4 = (android.widget.ImageView) bindings[5];
        this.imvEvaluate5 = (android.widget.ImageView) bindings[6];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.vm == variableId) {
            setVm((com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVm(@Nullable com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel Vm) {
        this.mVm = Vm;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.vm);
        super.requestRebind();
    }
    @Nullable
    public com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel getVm() {
        return mVm;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeVmMObservableString((android.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeVmMObservableString(android.databinding.ObservableField<java.lang.String> VmMObservableString, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel vm = mVm;
        java.lang.String vmMObservableStringGet = null;
        android.databinding.ObservableField<java.lang.String> vmMObservableString = null;

        if ((dirtyFlags & 0x7L) != 0) {



                if (vm != null) {
                    // read vm.mObservableString
                    vmMObservableString = vm.mObservableString;
                }
                updateRegistration(0, vmMObservableString);


                if (vmMObservableString != null) {
                    // read vm.mObservableString.get()
                    vmMObservableStringGet = vmMObservableString.get();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, vmMObservableStringGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityAdminHomeBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAdminHomeBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAdminHomeBinding>inflate(inflater, com.mvvm.kien2111.mvvmapplication.R.layout.activity_admin_home, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAdminHomeBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAdminHomeBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.mvvm.kien2111.mvvmapplication.R.layout.activity_admin_home, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAdminHomeBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAdminHomeBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_admin_home_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAdminHomeBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): vm.mObservableString
        flag 1 (0x2L): vm
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}