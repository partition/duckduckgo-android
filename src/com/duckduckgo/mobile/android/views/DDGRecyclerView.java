package com.duckduckgo.mobile.android.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class DDGRecyclerView extends RecyclerView {

  final private AdapterDataObserver observer = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      checkIfEmpty();
    }
  };
  private View emptyView;

  public DDGRecyclerView(Context context) {
    super(context);
  }

  public DDGRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DDGRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  void checkIfEmpty() {
    if (emptyView != null && getAdapter() != null) {
      final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
      emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
      setVisibility(emptyViewVisible ? GONE : VISIBLE);
    }
  }

  @Override
  public void setAdapter(Adapter adapter) {
    final Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(observer);
    }
    super.setAdapter(adapter);
    if (adapter != null) {
      adapter.registerAdapterDataObserver(observer);
    }

    checkIfEmpty();
  }

  public void setEmptyView(View emptyView) {
    this.emptyView = emptyView;
    checkIfEmpty();
  }
}