package com.mvvm.kien2111.mvvmapplication.ui.login;

import com.mvvm.kien2111.mvvmapplication.model.Credential;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LoginModule_ProvideCredentialFactory implements Factory<Credential> {
  private final LoginModule module;

  public LoginModule_ProvideCredentialFactory(LoginModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Credential get() {
    return Preconditions.checkNotNull(
        module.provideCredential(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Credential> create(LoginModule module) {
    return new LoginModule_ProvideCredentialFactory(module);
  }
}
