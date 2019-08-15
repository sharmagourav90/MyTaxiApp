package com.example.mytaxi.ui.list;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mytaxi.BR;
import com.example.mytaxi.R;
import com.example.mytaxi.data.model.PoiValues;
import com.example.mytaxi.databinding.RecyclerItemBinding;

import java.util.List;

public class TaxiListAdapter extends
        RecyclerView.Adapter<TaxiListAdapter.TaxiViewHolder> implements MyClickListener{

    private final List<PoiValues> mTaxiList;
    private final TaxiAdapterOnItemClickHandler mClickHandler;

    class TaxiViewHolder extends RecyclerView.ViewHolder{
        public RecyclerItemBinding itemRowBinding;

        public TaxiViewHolder(RecyclerItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.poiValues, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public TaxiListAdapter(List<PoiValues> taxiList, TaxiAdapterOnItemClickHandler clickHandler) {
        this.mTaxiList = taxiList;
        mClickHandler = clickHandler;
    }

    public interface TaxiAdapterOnItemClickHandler {
        void onItemClick(PoiValues values);
    }
    @Override
    public TaxiViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        RecyclerItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recycler_item, parent, false);

        return new TaxiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TaxiViewHolder holder,
                                 int position) {

        PoiValues dataModel = mTaxiList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setItemClickListener(this);
    }
    public void cardClicked(PoiValues f) {
        mClickHandler.onItemClick(f);
    }

    @Override
    public int getItemCount() {
        return mTaxiList.size();
    }
}
