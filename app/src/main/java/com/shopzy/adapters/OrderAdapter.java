package com.shopzy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.models.CartItem;
import com.shopzy.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        
        holder.tvOrderId.setText("Order #" + order.getOrderId());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy HH:mm", Locale.getDefault());
        holder.tvDate.setText("Placed on " + sdf.format(new Date(order.getTimestamp())));
        
        StringBuilder itemsSummary = new StringBuilder();
        int itemTotalQty = 0;
        for (CartItem item : order.getItems()) {
            itemTotalQty += item.getQuantity();
            if (itemsSummary.length() > 0) itemsSummary.append(", ");
            itemsSummary.append(item.getProduct().getName());
        }
        holder.tvItemsSummary.setText(itemTotalQty + " Items: " + itemsSummary.toString());
        holder.tvTotal.setText(String.format(Locale.getDefault(), "Total: ₹%.2f", order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvDate, tvStatus, tvItemsSummary, tvTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.orderId);
            tvDate = itemView.findViewById(R.id.orderDate);
            tvStatus = itemView.findViewById(R.id.orderStatus);
            tvItemsSummary = itemView.findViewById(R.id.orderItemsSummary);
            tvTotal = itemView.findViewById(R.id.orderTotal);
        }
    }
}
