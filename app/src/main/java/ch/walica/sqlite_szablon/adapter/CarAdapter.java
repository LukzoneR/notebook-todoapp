package ch.walica.sqlite_szablon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.walica.sqlite_szablon.R;
import ch.walica.sqlite_szablon.model.Car;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private List<Car> cars;
    private OnCarClickListener onCarClickListener;


    public CarAdapter(List<Car> cars, OnCarClickListener onCarClickListener) {
        this.cars = cars;
        this.onCarClickListener = onCarClickListener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new CarViewHolder(view, onCarClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.bind(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
