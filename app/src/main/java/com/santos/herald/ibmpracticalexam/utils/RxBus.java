package com.santos.herald.ibmpracticalexam.utils;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class RxBus {

    private static PublishSubject<Object> sSubject = PublishSubject.create();

    private RxBus() {
        // hidden constructor
    }


    public static Disposable subscribe(@NonNull Consumer<Object> action) {
        return sSubject.subscribe(action);
    }

    public static void publish(@NonNull Object message) {
        sSubject.onNext(message);
    }
}