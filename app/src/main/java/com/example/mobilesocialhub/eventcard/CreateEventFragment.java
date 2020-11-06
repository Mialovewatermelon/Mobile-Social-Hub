package com.example.mobilesocialhub.eventcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.ActivityCreateEventBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateEventFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {
    ActivityCreateEventBinding mBinding;
    FirebaseDatabase database;
    EditText eventPlaceText;
    TextView eventDateText;
    TextView eventTimeText;
    ImageButton datePickBtn;
    ImageButton timePickBtn;
    ImageView submitBtn;
    DatabaseReference eventRef;
    String datePublished;
    FragmentManager fm;
    TextView activityChosen;
    CircleImageView sport;
    CircleImageView coffee;
    CircleImageView dating;
    CircleImageView dining;
    CircleImageView shopping;
    CircleImageView work;
    String activityIcon;
    String username;
    boolean isChooseDate;
    boolean isChooseTime;
    boolean isChooseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
        isChooseActivity = false;
        isChooseDate = false;
        isChooseTime = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.activity_create_event,  container, false);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference().child("Events");

        eventPlaceText = mBinding.placeTextView;
        eventDateText = mBinding.dateText;
        eventTimeText = mBinding.timeText;
        datePickBtn = mBinding.datePickBtn;
        timePickBtn = mBinding.timePickBtn;
        submitBtn = mBinding.createEventBtn;
        coffee = mBinding.coffee;
        sport = mBinding.sport;
        dating = mBinding.dating;
        dining = mBinding.dining;
        shopping = mBinding.shopping;
        activityChosen=mBinding.activityChosen;
        work=mBinding.work;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        datePublished = simpleDateFormat.format(date);

        submitBtn.setOnClickListener(this);
        datePickBtn.setOnClickListener(this);
        timePickBtn.setOnClickListener(this);

        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               activityChosen.setText("Let's go for a coffee!");
               activityIcon = "coffee";
               Log.w("create","I click");
               isChooseActivity = true;
            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChosen.setText("Let's go shopping");
                activityIcon = "shopping";
                isChooseActivity = true;
            }
        });

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChosen.setText("Let's work together");
                activityIcon = "work";
                isChooseActivity = true;
            }
        });

        dining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChosen.setText("Let's dining out");
                activityIcon = "dining";
                isChooseActivity = true;
            }
        });

        dating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChosen.setText("Let's go for a date");
                activityIcon = "date";
                isChooseActivity = true;
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChosen.setText("Let's exercise together");
                activityIcon = "sport";
                isChooseActivity = true;
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_event_btn:
                String address = eventPlaceText.getText().toString();
                if (!isChooseTime || !isChooseDate || !isChooseActivity || address.equals("") ) {
                    Toast.makeText(v.getContext(), "Please finish all choices", Toast.LENGTH_LONG).show();
                    break;
                }
                String usernamePublished = this.username;
                String datePublished = this.datePublished;
                String eventDate = this.eventDateText.getText().toString();
                String eventTime = this.eventTimeText.getText().toString();
                String uuid = UUID.randomUUID().toString();
                String activityName = this.activityIcon;
                Event newEvent = new Event(usernamePublished, datePublished, eventDate, eventTime, address, uuid,activityName);
                eventRef.child(uuid).setValue(newEvent);
                Toast.makeText(v.getContext(), "Event has been created", Toast.LENGTH_LONG).show();
                break;
            case R.id.date_pick_btn:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                isChooseDate = true;
                break;

            case R.id.time_pick_btn:
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                        this,
                        true
                );
                tpd.show(getFragmentManager(), "Datepickerdialog");
                isChooseTime = true;
                break;



        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = new String(year+"-" + (monthOfYear + 1) + "-" + dayOfMonth);
        eventDateText.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = new String(hourOfDay+ ":" + minute);
        eventTimeText.setText(time);
    }


    public void setUsername(String username){
        this.username=username;
    }
}