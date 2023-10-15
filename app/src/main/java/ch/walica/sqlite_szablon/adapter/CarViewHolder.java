package ch.walica.sqlite_szablon.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.walica.sqlite_szablon.R;
import ch.walica.sqlite_szablon.model.Car;

public class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvTitle, tvBody, tvDate;
    private ImageView ivDelete, ivEdit;
    private OnCarClickListener onCarClickListener;


    public CarViewHolder(@NonNull View itemView, OnCarClickListener onCarClickListener) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvBody = itemView.findViewById(R.id.tvBody);
        tvDate = itemView.findViewById(R.id.tvDate);
        ivDelete = itemView.findViewById(R.id.ivDelete);
        ivEdit = itemView.findViewById(R.id.ivEdit);
        this.onCarClickListener = onCarClickListener;

        ivDelete.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
    }

    public void bind(Car car) {
        tvTitle.setText(car.getTitle());
        tvBody.setText(car.getBody());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(car.getCreatedDate());
        String dateStr = sdf.format(date);
        tvDate.setText(dateStr);
    }

    @Override
    public void onClick(View view) {
        onCarClickListener.onCarClick(getAdapterPosition(), view);
    }
}
