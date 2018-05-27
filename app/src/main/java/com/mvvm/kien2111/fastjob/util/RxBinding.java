package com.mvvm.kien2111.fastjob.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by WhoAmI on 21/03/2018.
 */

public class RxBinding {
    @CheckResult
    @NonNull
    public static Observable<String> fromSearchView(@NonNull final SearchView searchView){
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    subject.onNext(newText);
                }
                return true;
            }
        });
        return subject;
    }
    public static Observable<View> fromViewRaplidClick(@NonNull final View v){
        final BehaviorSubject<View> subject = BehaviorSubject.create();
        v.setOnClickListener(subject::onNext);
        return subject;
    }
}
