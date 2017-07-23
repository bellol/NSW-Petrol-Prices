package com.bellng.fuelprices;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellng.fuelprices.dto.ServiceStation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bell on 19-Nov-16.
 */

public class ServiceStationAdapter extends RecyclerView.Adapter<ServiceStationAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.distance)
        TextView distance;

        OnClickListener listener;

        public ViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }

        public interface OnClickListener {
            void onClick(int position);
        }
    }

    List<ServiceStation> serviceStations;
    OnSelect listener;

    public ServiceStationAdapter(OnSelect listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_service_stations, parent, false);
        return new ViewHolder(view, new ViewHolder.OnClickListener() {
            @Override
            public void onClick(int position) {
                listener.onSelect(serviceStations.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ServiceStation serviceStation = serviceStations.get(position);
        holder.name.setText(serviceStation.getName());
        holder.price.setText("" + serviceStation.getPrice() + " c/L");
        holder.address.setText(serviceStation.getAddress());
        holder.distance.setText("" + serviceStation.getDistance() + " km");
    }

    @Override
    public int getItemCount() {
        return serviceStations == null ? 0 : serviceStations.size();
    }

    public void setServiceStations(List<ServiceStation> serviceStations) {
        this.serviceStations = serviceStations;
    }

    public interface OnSelect {
        void onSelect(ServiceStation serviceStation);
    }

}
