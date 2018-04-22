package com.santos.herald.ibmpracticalexam.ui.base;

public interface PresenterFactory<P extends BasePresenter> {

    P create();
}