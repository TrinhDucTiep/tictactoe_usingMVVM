package com.example.tic_tac_toemvvm;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tic_tac_toemvvm.databinding.ActivityMainBinding;
import com.example.tic_tac_toemvvm.model.Player;
import com.example.tic_tac_toemvvm.viewmodel.GameViewModel;


public class MainActivity extends AppCompatActivity {
    private static final String NO_WINNER = "No One";
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    public void initDataBinding(){
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
//        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
//        gameViewModel = new GameViewModel();
        gameViewModel.init("P1", "P2");
        activityMainBinding.setGameViewModel(gameViewModel);
        setUpOnGameEndListener();
    }

    public void setUpOnGameEndListener(){
        gameViewModel.getWinner().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                onGameWinnerChange(player);
            }
        });
    }

    @VisibleForTesting
    public void onGameWinnerChange(Player winner){
        String winnerName = (winner != null && (winner.name != null && !winner.name.isEmpty()))
                ? winner.name : NO_WINNER;

        Toast.makeText(this, "Winner is " + winnerName, Toast.LENGTH_SHORT).show();
    }
}