package com.ozgursakizli.offlineapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ozgursakizli.offlineapp.database.DatabaseHelper;
import com.ozgursakizli.offlineapp.fragments.ProductDetailFragment;
import com.ozgursakizli.offlineapp.models.ProductDetailModel;
import com.ozgursakizli.offlineapp.models.ProductListModel;
import com.ozgursakizli.offlineapp.service.requests.BaseJsonObjectRequest;
import com.ozgursakizli.offlineapp.R;
import com.ozgursakizli.offlineapp.adapters.ProductListAdapter;
import com.ozgursakizli.offlineapp.base.BaseAppCompatActivity;
import com.ozgursakizli.offlineapp.database.ProductListScheme;
import com.ozgursakizli.offlineapp.models.ProductModel;
import com.ozgursakizli.offlineapp.service.enums.RequestSuffix;
import com.ozgursakizli.offlineapp.service.interfaces.IServiceObjectResponse;
import com.ozgursakizli.offlineapp.utils.StringConstants;
import com.ozgursakizli.offlineapp.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements ProductListAdapter.OnProductListItemClicked {

    private ProgressBar progressBar;
    private CoordinatorLayout container;
    private ProductListAdapter adapter;
    private Fragment currentFragment;
    private List<ProductModel> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        container = (CoordinatorLayout) findViewById(R.id.main_container);
        progressBar = (ProgressBar) findViewById(R.id.loading);
        adapter = new ProductListAdapter(MainActivity.this, productList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        RecyclerView productList = (RecyclerView) findViewById(R.id.rv_productlist);
        productList.setHasFixedSize(true);
        productList.setAdapter(adapter);
        productList.setLayoutManager(layoutManager);
        initList();
    }

    private void initList() {
        progressBar.setVisibility(View.VISIBLE);

        if (!Util.isNetworkActive()) {
            Snackbar.make(container, R.string.connection_error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.hide, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
            try {
                Cursor cursor = DatabaseHelper.getInstance(MainActivity.this).getAllData();
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    ProductModel productModel = new ProductModel();
                    productModel.setProductId(cursor.getString(ProductListScheme.ProductListTable.Columns.productIDKey));
                    productModel.setName(cursor.getString(ProductListScheme.ProductListTable.Columns.productNameKey));
                    productModel.setPrice(cursor.getString(ProductListScheme.ProductListTable.Columns.productPriceKey));
                    productModel.setImage(cursor.getString(ProductListScheme.ProductListTable.Columns.productImageKey));
                    productList.add(productModel);
                    cursor.moveToNext();
                }

                cursor.close();
                adapter.notifyDataSetChanged();
            } catch (Exception ex) {
                showToast(R.string.failed);
                ex.printStackTrace();
            } finally {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            getProductList();
        }
    }

    private void getProductList() {
        new BaseJsonObjectRequest.Builder().url(RequestSuffix.PRODUCT_LIST, "").serviceListener(new IServiceObjectResponse() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Gson gson = new Gson();
                    ProductListModel productListModel = gson.fromJson(jsonObject.toString(), ProductListModel.class);
                    productList.clear();
                    productList.addAll(productListModel.getProductList());
                    adapter.notifyDataSetChanged();
                    DatabaseHelper.getInstance(MainActivity.this).deleteAllData();
                    DatabaseHelper.getInstance(MainActivity.this).insertData(productList);
                } catch (Exception ex) {
                    showToast(R.string.failed);
                    ex.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                showToast(R.string.failed);
                progressBar.setVisibility(View.GONE);
            }
        }).build();
    }

    @Override
    public void onItemClicked(int position) {
        getProductDetail(productList.get(position));
    }

    private void getProductDetail(ProductModel productModel) {
        progressBar.setVisibility(View.VISIBLE);

        new BaseJsonObjectRequest.Builder().url(RequestSuffix.PRODUCT_DETAIL, productModel.getProductId()).serviceListener(new IServiceObjectResponse() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Gson gson = new Gson();
                    ProductDetailModel productDetail = gson.fromJson(jsonObject.toString(), ProductDetailModel.class);
                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(StringConstants.PRODUCT_DETAIL, productDetail);
                    productDetailFragment.setArguments(bundle);
                    openFragment(productDetailFragment);
                } catch (Exception ex) {
                    showToast(R.string.failed);
                    ex.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                showToast(R.string.failed);
                progressBar.setVisibility(View.GONE);
            }
        }).build();
    }

    private void openFragment(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, currentFragment);
        fragmentTransaction.commit();
    }

    public void removeFragment() {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            currentFragment = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        } else if (currentFragment != null) {
            removeFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnected() {
        super.onConnected();
        getProductList();
    }

}
