
package android.databinding;
import com.mvvm.kien2111.mvvmapplication.BR;
@javax.annotation.Generated("Android Data Binding")
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 19;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.mvvm.kien2111.mvvmapplication.R.layout.activity_login:
                    return com.mvvm.kien2111.mvvmapplication.databinding.ActivityLoginBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.fragment_search:
                    return com.mvvm.kien2111.mvvmapplication.databinding.FragmentSearchBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.fragment_feed:
                    return com.mvvm.kien2111.mvvmapplication.databinding.FragmentFeedBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.fragment_user:
                    return com.mvvm.kien2111.mvvmapplication.databinding.FragmentUserBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.profile_item:
                    return com.mvvm.kien2111.mvvmapplication.databinding.ProfileItemBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.fragment_favouriteprofile:
                    return com.mvvm.kien2111.mvvmapplication.databinding.FragmentFavouriteprofileBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.activity_signup:
                    return com.mvvm.kien2111.mvvmapplication.databinding.ActivitySignupBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.activity_admin_home:
                    return com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminHomeBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.activity_universal:
                    return com.mvvm.kien2111.mvvmapplication.databinding.ActivityUniversalBinding.bind(view, bindingComponent);
                case com.mvvm.kien2111.mvvmapplication.R.layout.fragment_notification:
                    return com.mvvm.kien2111.mvvmapplication.databinding.FragmentNotificationBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -237232145: {
                if(tag.equals("layout/activity_login_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.activity_login;
                }
                break;
            }
            case -1648731965: {
                if(tag.equals("layout/fragment_search_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.fragment_search;
                }
                break;
            }
            case -1182726727: {
                if(tag.equals("layout/fragment_feed_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.fragment_feed;
                }
                break;
            }
            case -740346714: {
                if(tag.equals("layout/fragment_user_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.fragment_user;
                }
                break;
            }
            case -550400289: {
                if(tag.equals("layout/profile_item_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.profile_item;
                }
                break;
            }
            case -1415032453: {
                if(tag.equals("layout/fragment_favouriteprofile_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.fragment_favouriteprofile;
                }
                break;
            }
            case -474702252: {
                if(tag.equals("layout/activity_signup_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.activity_signup;
                }
                break;
            }
            case -1160314421: {
                if(tag.equals("layout/activity_admin_home_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.activity_admin_home;
                }
                break;
            }
            case 1690790865: {
                if(tag.equals("layout/activity_universal_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.activity_universal;
                }
                break;
            }
            case -1802862650: {
                if(tag.equals("layout/fragment_notification_0")) {
                    return com.mvvm.kien2111.mvvmapplication.R.layout.fragment_notification;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"VMfavouriteprofile"
            ,"VMfeed"
            ,"VMnotification"
            ,"VMsearch"
            ,"VMuniversal"
            ,"VMuser"
            ,"obj"
            ,"vm"};
    }
}