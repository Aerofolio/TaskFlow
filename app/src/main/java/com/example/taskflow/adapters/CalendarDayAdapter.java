package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.CalendarDayViewHolder> {

    private List<Date> dates;

    private Date selectedDate;

    public CalendarDayAdapter(List<Date> dates, Date selectedDate) {
        this.dates = dates;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public CalendarDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarDayViewHolder holder, int position) {
        Date date = dates.get(position);

        SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        SimpleDateFormat weekDayFormat = new SimpleDateFormat("EEE", new Locale("pt", "BR"));

        holder.textViewMonthDay.setText(dayMonthFormat.format(date));
        holder.textViewWeekDay.setText(weekDayFormat.format(date));

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(selectedDate);
        boolean isSameDay =
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        if (isSameDay) {
            holder.cardViewDate.setCardBackgroundColor(holder.cardViewDate.getContext().getResources().getColor(R.color.task_flow_purple_light));
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class CalendarDayViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMonthDay;
        TextView textViewWeekDay;
        CardView cardViewDate;

        public CalendarDayViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMonthDay = itemView.findViewById(R.id.textViewMonthDay);
            textViewWeekDay = itemView.findViewById(R.id.textViewWeekDay);
            cardViewDate = itemView.findViewById(R.id.cardViewDate);;
        }
    }
}