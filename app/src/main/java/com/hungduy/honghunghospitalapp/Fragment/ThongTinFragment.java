package com.hungduy.honghunghospitalapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hungduy.honghunghospitalapp.Activity.WebviewActivity;
import com.hungduy.honghunghospitalapp.Adapter.TinTucAdapter;
import com.hungduy.honghunghospitalapp.Adapter.TinTucHorizontalAdapter;
import com.hungduy.honghunghospitalapp.Model.ResponseModel;
import com.hungduy.honghunghospitalapp.Model.getModel.getTinTuc;
import com.hungduy.honghunghospitalapp.R;
import com.hungduy.honghunghospitalapp.Utility.CallbackResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class ThongTinFragment extends BaseFragment {
    private ArrayList<getTinTuc> getTinTucChuongTrinhUuDai= new ArrayList<>();
    private ArrayList<getTinTuc> getTinTucThongBaoQuyDinh= new ArrayList<>();
    private ArrayList<getTinTuc> getTinTucKhac= new ArrayList<>();
    private RecyclerView rcChuongTrinh,rcThongBaoQuyDinh,rcThongBaoKhac;
    private TextView txtThongBao,txtTinKhac,txtCTUD;
    private ArrayList<String> ttdadoc;
    private TinTucAdapter QuyDinhADT,KhacADT;
    private TinTucHorizontalAdapter tinTucHoADT;
    public ThongTinFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView(view);
        ttdadoc = new ArrayList<>();


        LinearLayoutManager linearLayoutManagerHo = new LinearLayoutManager(getContext());
        linearLayoutManagerHo.setOrientation(RecyclerView.HORIZONTAL);
        tinTucHoADT= new TinTucHorizontalAdapter(getTinTucChuongTrinhUuDai,getContext(), getActivity());
        rcChuongTrinh.setAdapter(tinTucHoADT);
        rcChuongTrinh.setLayoutManager(linearLayoutManagerHo);

        LinearLayoutManager layoutQD = new LinearLayoutManager(getContext());
        QuyDinhADT= new TinTucAdapter(getTinTucThongBaoQuyDinh, getContext(), getActivity(), ttdadoc, new TinTucAdapter.updateViewed() {
            @Override
            public void onViewed(String ma) {
                handleViewed(ma);
            }
        });
        rcThongBaoQuyDinh.setAdapter(QuyDinhADT);
        rcThongBaoQuyDinh.setLayoutManager(layoutQD);


        LinearLayoutManager layoutKhac = new LinearLayoutManager(getContext());
        KhacADT= new TinTucAdapter(getTinTucKhac, getContext(), getActivity(), ttdadoc, new TinTucAdapter.updateViewed() {
            @Override
            public void onViewed(String ma) {
                handleViewed(ma);
            }
        });
        rcThongBaoKhac.setAdapter(KhacADT);
        rcThongBaoKhac.setLayoutManager(layoutKhac);


      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                getTinTucs.clear();
                getTinTucChuongTrinhUuDai.clear();
                if(tinTucDAO.getAll().size() > 0){
                    for(TinTuc a : tinTucDAO.getAll()){
                        getTinTucs.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                        if(a.getLoai() == 4){
                            getTinTucChuongTrinhUuDai.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                            tinTucHoADT.notifyDataSetChanged();
                        }
                        if(a.getLoai() == 1){
                            getTinTucThongBaoQuyDinh.add(new getTinTuc(a.getID(),a.getLoai(),a.getTitle(),a.getMoTa(),a.getURL()));
                            QuyDinhADT.notifyDataSetChanged();
                        }
                        tinTucADT.notifyDataSetChanged();
                    }
                    Log.d(TAG,"localDATA");
                }else{

                }
            }
        });*/ // code này đang lỗi
        txtCTUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn/category/tin-tuc/tin-hong-hung/");
                startActivity(intent);
            }
        });

        txtThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn/category/tin-tuc/thong-bao/");
                startActivity(intent);
            }
        });
        txtTinKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url","https://honghunghospital.com.vn/category/tin-tuc/");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        String tt = getStringPreferences(preferences,"tintucdadoc");
        if(!tt.isEmpty()){
            try{
                String[] tindadoc = new Gson().fromJson(tt,String[].class);
                if(tindadoc != null){
                    ttdadoc.addAll(Arrays.asList(tindadoc));
                }
            }catch (Exception e){
            }
        }
        mAPIService.getTinTuc(APIKey).enqueue(new CallbackResponse(getActivity()){
            @Override
            public void success(Response<ResponseModel> response) {
                try{
                    getTinTuc[] tinTucs = new Gson().fromJson(response.body().getData(),getTinTuc[].class);
                    if(tinTucs.length > 0){
                        int i=0;
                        getTinTucChuongTrinhUuDai.clear();
                        getTinTucThongBaoQuyDinh.clear();
                        getTinTucKhac.clear();
                        for (getTinTuc a: tinTucs) {
                            if(a.getLoai() == 4 && getTinTucChuongTrinhUuDai.size() < 3){
                                getTinTucChuongTrinhUuDai.add(a);
                            }
                            if(a.getLoai() == 1 && getTinTucThongBaoQuyDinh.size() < 3){
                                getTinTucThongBaoQuyDinh.add(a);
                            }
                            if(a.getLoai() == 3 && getTinTucKhac.size() < 5){
                                getTinTucKhac.add(a);
                            }
                        }
                        tinTucHoADT.notifyDataSetChanged();
                        QuyDinhADT.notifyDataSetChanged();
                        KhacADT.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Đã có lỗi khi lấy tin tức !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        QuyDinhADT.notifyDataSetChanged();
        KhacADT.notifyDataSetChanged();
    }

    private void handleViewed(String ma) {
        String tt = getStringPreferences(preferences,"tintucdadoc");
        if(tt.isEmpty()){
            String[] ttd = new String[1];
            ttd[0] = ma;
            setStringPreferences(preferences,"tintucdadoc",new Gson().toJson(ttd));
        }else{
            try {
                String[] tindadoc = new Gson().fromJson(tt, String[].class);
                String[] ttd = new String[tindadoc.length+1];
                for(int i =0 ;i<tindadoc.length;i++){
                    ttd[i] = tindadoc[i];
                }
                ttd[tindadoc.length] = ma;
                setStringPreferences(preferences,"tintucdadoc",new Gson().toJson(ttd));
            }catch (Exception e){

            }
        }
    }

    private void mapView(View view) {
        rcThongBaoKhac = view.findViewById(R.id.rcThongBaoKhac);
        rcChuongTrinh = view.findViewById(R.id.rcChuongTrinh);
        rcThongBaoQuyDinh = view.findViewById(R.id.rcThongBaoQuyDinh);
        txtThongBao = view.findViewById(R.id.txtThongBao);
        txtTinKhac = view.findViewById(R.id.txtTinKhac);
        txtCTUD = view.findViewById(R.id.txtCTUD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin, container, false);
    }
}