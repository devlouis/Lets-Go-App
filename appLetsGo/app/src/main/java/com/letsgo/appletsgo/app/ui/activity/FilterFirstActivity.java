package com.letsgo.appletsgo.app.ui.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letsgo.appletsgo.R;
import com.letsgo.appletsgo.app.ui.component.BuildConcierto;
import com.letsgo.appletsgo.app.ui.component.CustomTextView;
import com.letsgo.appletsgo.app.ui.core.BaseAppCompat;
import com.letsgo.appletsgo.app.utils.LogUtils;
import com.letsgo.appletsgo.data.store.SessionUser;
import com.letsgo.appletsgo.domain.model.entity.Categories;
import com.letsgo.appletsgo.domain.model.entity.Subcategories;
import com.letsgo.appletsgo.domain.model.entity.TypeCategoriesList;
import com.letsgo.appletsgo.presenter.CategoriesPresenter;
import com.letsgo.appletsgo.view.CategoriesView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterFirstActivity extends BaseAppCompat implements CategoriesView{
    private static final String TAG = "FilterFirstActivity";

    @BindView(R.id.llaCategories) LinearLayout llaCategories;
    @BindView(R.id.tviGeneral) TextView tviGeneral;
    @BindView(R.id.tviAdultos) TextView tviAdultos;
    @BindView(R.id.tviNinos) TextView tviNinos;
    @BindView(R.id.tviTerceraEdad) TextView tviTerceraEdad;
    @BindView(R.id.tviName) TextView tviName;
    @BindView(R.id.vieLoading) View vieLoading;

    private boolean statusGeneral = true;
    private boolean statusAdultos = false;
    private boolean statusNinos = false;
    private boolean statusTerceraEdad = false;
    private CategoriesPresenter categoriesPresenter;
    private LinearLayout linearLayout;
    private LinearLayout subLinearLayout;
    private LinearLayout subcontentLinearLayout;
    private boolean sublinearLayoutAdded = false;
    private int positionCategory = 0;
    private List<Categories> categoriesToSendList;
    private List<Subcategories> subcategoriesToSendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_first);
        ButterKnife.bind(this);

        tviName.setText("Hola   " + SessionUser.getSessionUser(this).getFirst_name());

        initPresenter();
        categoriesPresenter.getCategories();
        categoriesToSendList = new ArrayList<>();
        subcategoriesToSendList = new ArrayList<>();

        //TODO 2017-03-29 19:30:00
        String input_date="01-08-2017";
        Date dt1 = null;
        Locale locale = new Locale ( "es" , "ES" );
        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy", locale);

        try {
            dt1=format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayOfTheWeek = (String) DateFormat.format("EEEE", dt1);
        String day          = (String) DateFormat.format("dd",   dt1);
        String monthString  = (String) DateFormat.format("MMM",  dt1);
        String monthNumber  = (String) DateFormat.format("MM",   dt1);
        String year         = (String) DateFormat.format("yyyy", dt1);
        LogUtils.v(TAG, " fecha: " + dayOfTheWeek);
        LogUtils.v(TAG, " fecha: " + day);
        LogUtils.v(TAG, " fecha: " + monthString);
        LogUtils.v(TAG, " fecha: " + monthNumber);
        LogUtils.v(TAG, " fecha: " + dayOfTheWeek);
        LogUtils.v(TAG, " fecha: " + year);

        /*try {
            String _24HourTime = "20:00";
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.v(TAG, " fecha: " + _24HourDt);
        LogUtils.v(TAG, " fecha: " + _12HourSDF.format(_24HourDt));*/


    }

    private void initPresenter(){
        categoriesPresenter = new CategoriesPresenter();
        categoriesPresenter.attachedView(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getCategories(TypeCategoriesList typeCategoriesList) {
        setCategoriesToScreen(typeCategoriesList);
    }

    @Override
    public void showLoading() {
        vieLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        vieLoading.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyCategories() {

    }

    @Override
    public void showMessageError(String message) {

    }

    public class ViewHolderTeatro {
        @BindView(R.id.tviComedia) TextView tviComedia;

        public ViewHolderTeatro(View container) {
            ButterKnife.bind(this,container);
        }
    }

    private void setCategoriesToScreen(TypeCategoriesList typeCategoriesList){
        final List<Categories> categoriesList = typeCategoriesList.getCategoriesList();
        initLinearLayout();
        for(int i = 0; i< categoriesList.size(); i++){
            final Categories categories = categoriesList.get(i);
            if(i > 0 && i % 3 == 0){
                llaCategories.addView(linearLayout);
                initLinearLayout();
            }
            final CustomTextView tviCategory = new CustomTextView(this);
            tviCategory.setText(categories.getDescription());
            tviCategory.setBackgroundResource(R.drawable.type_public_off);
            tviCategory.setTextColor(getResources().getColor(R.color.secondary_text));
            tviCategory.setGravity(Gravity.CENTER);
            tviCategory.setPadding(40, 40, 40, 40);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 20, 30);
            tviCategory.setLayoutParams(layoutParams);
            linearLayout.addView(tviCategory);
            if(i == categoriesList.size() - 1){
                if(llaCategories.getChildCount() != 3)
                    llaCategories.addView(linearLayout);
            }
            final int finalI = i;
            tviCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<Subcategories> subcategoriesList = categories.getSubcategoriesList();
                    if(categories.isSelected()){
                        categories.setSelected(false);
                        showPublicTypeChange(false, tviCategory);
                        sublinearLayoutAdded = false;
                        if(subLinearLayout != null && subLinearLayout.getChildCount() > 0){
                            llaCategories.removeViewAt(positionCategory + 1);
                            subLinearLayout.removeAllViews();
                            subcontentLinearLayout.removeAllViews();
                        }
                        for(Subcategories subcategories : subcategoriesList){
                            subcategories.setSelected(false);
                        }
                        for(int i = 0; i<categoriesToSendList.size(); i++){
                            Categories categorieToDelete = categoriesToSendList.get(i);
                            if(categorieToDelete.getId_activities_types() == categories.getId_activities_types()){
                                categoriesToSendList.remove(i);
                                break;
                            }
                        }
                    } else {
                        subcategoriesToSendList = new ArrayList<Subcategories>();
                        Categories categoriesToSend = new Categories();
                        categoriesToSend.setId_activities_types(categories.getId_activities_types());
                        categoriesToSend.setDescription(categories.getDescription());
                        categoriesToSend.setSelected(true);
                        categoriesToSendList.add(categoriesToSend);
                        categories.setSelected(true);
                        showPublicTypeChange(true, tviCategory);
                        if (sublinearLayoutAdded) {
                            subLinearLayout.removeAllViews();
                            llaCategories.removeViewAt(positionCategory + 1);
                        }
                        if (subcategoriesList.size() > 0) {
                            Subcategories subcategoriesToSend = new Subcategories();
                            subcategoriesToSend.setId_activities_subtypes(subcategoriesList.get(0).getId_activities_subtypes());
                            subcategoriesToSend.setSelected(true);
                            subcategoriesToSend.setDescription(subcategoriesList.get(0).getDescription());
                            subcategoriesToSendList.add(subcategoriesToSend);
                            categoriesToSendList.get(categoriesToSendList.size() - 1).setSubcategoriesList(subcategoriesToSendList);
                            initSublinearLayout();
                            subLinearLayout.setBackgroundResource(R.drawable.bg_sub_categoria_filtro);
                            initSubcontentLinearLayout();
                            for (int j = 0; j < subcategoriesList.size(); j++) {
                                final Subcategories subcategories = subcategoriesList.get(j);
                                if (j > 0 && j % 4 == 0) {
                                    subLinearLayout.addView(subcontentLinearLayout);
                                    initSubcontentLinearLayout();
                                }
                                final CustomTextView tviSubcategory = new CustomTextView(FilterFirstActivity.this);
                                tviSubcategory.setText(subcategories.getDescription());
                                if(j == 0){
                                    showSubcategoryChange(true, tviSubcategory);
                                } else {
                                    showSubcategoryChange(false, tviSubcategory);
                                }
                                tviSubcategory.setGravity(Gravity.CENTER);
                                tviSubcategory.setPadding(40, 40, 40, 40);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(10, 20, 10, 30);
                                tviSubcategory.setLayoutParams(layoutParams);
                                subcontentLinearLayout.addView(tviSubcategory);
                                if (j == subcategoriesList.size() - 1) {
                                    if (subcontentLinearLayout.getChildCount() != 4)
                                        subLinearLayout.addView(subcontentLinearLayout);
                                }
                                tviSubcategory.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(subcategories.isSelected()){
                                            if(!subcategories.getDescription().toLowerCase().equals("todos")){
                                                showSubcategoryChange(false, tviSubcategory);
                                                subcategories.setSelected(false);
                                            }
                                        } else{
                                            if(subcategories.getDescription().toLowerCase().equals("todos")){
                                                for(Subcategories subcategoriesToUnselect : subcategoriesList){
                                                    if(!subcategoriesToUnselect.getDescription().toLowerCase().equals("todos")){
                                                        subcategoriesToUnselect.setSelected(false);
                                                    }
                                                }
                                                for(int i = 0; i<subLinearLayout.getChildCount(); i++){
                                                    LinearLayout llaSubContent = (LinearLayout) subLinearLayout.getChildAt(i);
                                                    for(int j = 0; j<llaSubContent.getChildCount(); j++){
                                                        TextView tviSubcontent = (TextView) llaSubContent.getChildAt(j);
                                                        if(i == 0 && j > 0){
                                                            showSubcategoryChange(false, tviSubcontent);
                                                        } else{
                                                            showSubcategoryChange(false, tviSubcontent);
                                                        }
                                                    }
                                                }
                                                Subcategories subcategoriesToSend = new Subcategories();
                                                subcategoriesToSend.setId_activities_subtypes(subcategories.getId_activities_subtypes());
                                                subcategoriesToSend.setDescription(subcategories.getDescription());
                                                subcategoriesToSend.setSelected(true);
                                                subcategories.setSelected(true);
                                                showSubcategoryChange(true, tviSubcategory);
                                            } else{
                                                for(Subcategories subcategoriesToUnselect : subcategoriesList){
                                                    if(subcategoriesToUnselect.getDescription().toLowerCase().equals("todos")){
                                                        subcategoriesToUnselect.setSelected(false);
                                                        break;
                                                    }
                                                }
                                                for(int i = 0; i<subLinearLayout.getChildCount(); i++){
                                                    LinearLayout llaSubContent = (LinearLayout) subLinearLayout.getChildAt(i);
                                                    for(int j = 0; j<llaSubContent.getChildCount(); j++){
                                                        TextView tviSubcontent = (TextView) llaSubContent.getChildAt(j);
                                                        if(i == 0 && j == 0){
                                                            showSubcategoryChange(false, tviSubcontent);
                                                        }
                                                    }
                                                }
                                                showSubcategoryChange(true, tviSubcategory);
                                                subcategories.setSelected(true);
                                            }
                                        }
                                    }
                                });
                            }
                            positionCategory = (int) ((finalI) / 3);
                            llaCategories.addView(subLinearLayout, (positionCategory + 1));
                            sublinearLayoutAdded = true;
                        } else{
                            sublinearLayoutAdded = false;
                        }
                    }
                }
            });
        }
        selectOptionPublicType(true, false, false, false);
    }

    private void initLinearLayout(){
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
    }

    private void initSublinearLayout(){
        subLinearLayout = new LinearLayout(this);
        subLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        subLinearLayout.setOrientation(LinearLayout.VERTICAL);
        subLinearLayout.setGravity(Gravity.CENTER);
        subLinearLayout.setPadding(0, 30, 0, 0);
    }

    private void initSubcontentLinearLayout(){
        subcontentLinearLayout = new LinearLayout(this);
        subcontentLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        subcontentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        subcontentLinearLayout.setGravity(Gravity.CENTER);
    }

    @OnClick(R.id.tviGeneral)
    public void generalPublic(){
        selectOptionPublicType(true, false, false, false);
    }

    @OnClick(R.id.tviAdultos)
    public void adultsPublic(){
        selectOptionPublicType(false, true, false, false);
    }

    @OnClick(R.id.tviNinos)
    public void childrenPublic(){
        selectOptionPublicType(false, false, true, false);
    }

    @OnClick(R.id.tviTerceraEdad)
    public void oldPublic(){
        selectOptionPublicType(false, false, false, true);
    }

    @OnClick(R.id.btn_comenzar)
    public void beginApp(){
        //categoriesPresenter.saveCategoriesToPreferences(categoriesListToSend);
    }

    private void selectOptionPublicType(boolean statusGeneral, boolean statusAdultos, boolean statusNinos, boolean statusTerceraEdad){
        this.statusGeneral = statusGeneral;
        this.statusAdultos = statusAdultos;
        this.statusNinos = statusNinos;
        this.statusTerceraEdad = statusTerceraEdad;
        showPublicTypeChange(statusGeneral, tviGeneral);
        showPublicTypeChange(statusAdultos, tviAdultos);
        showPublicTypeChange(statusNinos, tviNinos);
        showPublicTypeChange(statusTerceraEdad, tviTerceraEdad);
    }

    public boolean showPublicTypeChange(boolean status, TextView tviCategory){
        boolean newStatus;
        if (status == true) {
            newStatus = false;
            tviCategory.setTextColor(getResources().getColor(R.color.white));
            tviCategory.setBackgroundResource(R.drawable.type_public_on);
        } else{
            newStatus = true;
            tviCategory.setTextColor(getResources().getColor(R.color.secondary_text));
            tviCategory.setBackgroundResource(R.drawable.type_public_off);
        }
        return newStatus;
    }

    public boolean showSubcategoryChange(boolean status, TextView tviSubcategory){
        boolean newStatus;
        if (status == false) {
            newStatus = true;
            tviSubcategory.setTextColor(getResources().getColor(R.color.white));
            tviSubcategory.setBackgroundResource(R.drawable.type_sub_category_off);
        } else{
            newStatus = false;
            tviSubcategory.setTextColor(getResources().getColor(R.color.color_text_on));
            tviSubcategory.setBackgroundResource(R.drawable.type_public_off);
        }
        return newStatus;
    }

}
