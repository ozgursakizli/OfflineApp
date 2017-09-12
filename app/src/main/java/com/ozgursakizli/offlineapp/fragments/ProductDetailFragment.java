package com.ozgursakizli.offlineapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ozgursakizli.offlineapp.R;
import com.ozgursakizli.offlineapp.base.BaseFragment;
import com.ozgursakizli.offlineapp.models.ProductDetailModel;
import com.ozgursakizli.offlineapp.utils.StringConstants;
import com.squareup.picasso.Picasso;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductDetailFragment extends BaseFragment {

    private ProductDetailModel productDetail;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.productDetail = args.getParcelable(StringConstants.PRODUCT_DETAIL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ImageView imgProduct = view.findViewById(R.id.img_product);
        TextView txtName = view.findViewById(R.id.txt_name);
        TextView txtDescription = view.findViewById(R.id.txt_description);

        if (productDetail != null) {
            if (productDetail.getImage() != null && !productDetail.getImage().equals("")) {
                Picasso.with(getActivity()).load(productDetail.getImage()).into(imgProduct);
                // Cache image
                Picasso.with(getActivity()).load(productDetail.getImage()).fetch();
            }

            txtName.setText(productDetail.getName());
            txtDescription.setText(productDetail.getDescription());
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
        }
    }

}
