package com.mvvm.kien2111.mvvmapplication.ui.signup;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SignUpViewModel_Factory implements Factory<SignUpViewModel> {
  private final MembersInjector<SignUpViewModel> signUpViewModelMembersInjector;

  public SignUpViewModel_Factory(MembersInjector<SignUpViewModel> signUpViewModelMembersInjector) {
    assert signUpViewModelMembersInjector != null;
    this.signUpViewModelMembersInjector = signUpViewModelMembersInjector;
  }

  @Override
  public SignUpViewModel get() {
    return MembersInjectors.injectMembers(signUpViewModelMembersInjector, new SignUpViewModel());
  }

  public static Factory<SignUpViewModel> create(
      MembersInjector<SignUpViewModel> signUpViewModelMembersInjector) {
    return new SignUpViewModel_Factory(signUpViewModelMembersInjector);
  }
}
