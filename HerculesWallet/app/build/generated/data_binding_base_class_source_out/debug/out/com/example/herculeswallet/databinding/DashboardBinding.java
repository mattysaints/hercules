// Generated by view binder compiler. Do not edit!
package com.example.herculeswallet.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.herculeswallet.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DashboardBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final RecyclerView listCrypto;

  @NonNull
  public final ImageView logoDashboard;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView6;

  private DashboardBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imageView,
      @NonNull RecyclerView listCrypto, @NonNull ImageView logoDashboard,
      @NonNull TextView textView, @NonNull TextView textView3, @NonNull TextView textView4,
      @NonNull TextView textView6) {
    this.rootView = rootView;
    this.imageView = imageView;
    this.listCrypto = listCrypto;
    this.logoDashboard = logoDashboard;
    this.textView = textView;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView6 = textView6;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DashboardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DashboardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dashboard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DashboardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.list_crypto;
      RecyclerView listCrypto = ViewBindings.findChildViewById(rootView, id);
      if (listCrypto == null) {
        break missingId;
      }

      id = R.id.logo_dashboard;
      ImageView logoDashboard = ViewBindings.findChildViewById(rootView, id);
      if (logoDashboard == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      return new DashboardBinding((ConstraintLayout) rootView, imageView, listCrypto, logoDashboard,
          textView, textView3, textView4, textView6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}