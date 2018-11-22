package com.udacity.android.bakingapp.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.model.Ingredient;
import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.model.Step;
import com.udacity.android.bakingapp.player.PlayerEventListener;
import com.udacity.android.bakingapp.ui.adapter.RecipeIngredientsAdapter;
import com.udacity.android.bakingapp.ui.viewmodel.RecipeSharedViewModel;
import com.udacity.android.bakingapp.ui.viewmodel.RecipeSharedViewModelFactory;
import com.udacity.android.bakingapp.utils.InjectorUtils;

import org.parceler.Parcels;

import java.util.List;

import static com.udacity.android.bakingapp.utils.BakingAppConstants.APPLICATION_NAME;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_INGREDIENTS_KEY;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_KEY;
import static com.udacity.android.bakingapp.utils.BakingAppConstants.RECIPE_SELECTED_ACTION_KEY;

public class RecipeInstructionsFragment extends BaseFragment {


    private static final String TAG = RecipeInstructionsFragment.class.getSimpleName();


    private PlayerEventListener playerEventListener;
    private Recipe mRecipe;
    private ListView mIngredientsListView;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private RecipeSharedViewModel mViewModel;
    private CardView mContainerIngredients;
    private CardView mContainerStep;
    private TextView mInstructionsText;
    private ConstraintLayout mNavigationLayout;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private int mPosition;
    private List<Ingredient> mIngredients;
    private Step mStep;
    private boolean isLastStep;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeSharedViewModelFactory factory = InjectorUtils.provideRecipeSharedViewModelFactory(getContext());
        mViewModel =  ViewModelProviders.of(getActivity(),factory).get(RecipeSharedViewModel.class);
        loadData(mRecipe.getId(),mPosition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_instructions, container, false);

        mContainerIngredients = view.findViewById(R.id.cv_container_ingredients);
        mContainerStep = view.findViewById(R.id.cv_container_steps);
        mInstructionsText = view.findViewById(R.id.tv_recipe_step_instructions);
        mNavigationLayout = view.findViewById(R.id.ll_navigation);
        mButtonNext = view.findViewById(R.id.btn_recipe_next_step);
        mButtonPrevious = view.findViewById(R.id.btn_recipe_previous_step);

        // Initialize the player view.
        mPlayerView = view.findViewById(R.id.playerView);

        mIngredientsListView = view.findViewById(R.id.lv_ingredients);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRecipe = Parcels.unwrap(getArguments().getParcelable(RECIPE_KEY));
            mPosition = getArguments().getInt(RECIPE_SELECTED_ACTION_KEY, -1);
        }
        if(isLandscapeMode()){
            mNavigationLayout.setVisibility(View.GONE);
        }else{
            mNavigationLayout.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void setRecipe(long recipeId, int position) {
       loadData(recipeId,position);
    }

    private void setStep() {
        showStep();
        if (!TextUtils.isEmpty(mStep.getVideoURL())) {
            initPlayer(mStep.getVideoURL());
        } else if (!TextUtils.isEmpty(mStep.getThumbnailURL())) {
            initPlayer(mStep.getThumbnailURL());
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
        mInstructionsText.setText(mStep.getDescription());
    }

    private void setAdapter() {
        RecipeIngredientsAdapter ingredientsAdapter = new RecipeIngredientsAdapter(mIngredients, getContext());
        mIngredientsListView.setAdapter(ingredientsAdapter);
    }

    private void initPlayer(String videoUrl) {
        initializeMediaSession();
        initializePlayer(Uri.parse(videoUrl));
        mPlayerView.setVisibility(View.VISIBLE);
    }

    private void showIngredients() {
        mContainerIngredients.setVisibility(View.VISIBLE);
        mContainerStep.setVisibility(View.GONE);
        mButtonPrevious.setEnabled(false);
        mButtonNext.setEnabled(true);
    }

    private void showStep() {
        mContainerIngredients.setVisibility(View.GONE);
        mContainerStep.setVisibility(View.VISIBLE);
        if(mStep.isLastStep()){
            mButtonPrevious.setEnabled(true);
            mButtonNext.setEnabled(false);
        }else{
            mButtonNext.setEnabled(true);
            mButtonPrevious.setEnabled(true);
        }
    }

    /* UDACITY EXAMPLE CODE*/
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // SessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new SessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            mMediaSession.setActive(false);
        }
    }


    private class SessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            createExoPlayer();
            play(mediaUri);
        } else {
            mExoPlayer.stop();
            play(mediaUri);
        }
    }

    private void createExoPlayer() {
        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);

        // Set the ExoPlayer.EventListener to this activity.
        playerEventListener = new PlayerEventListener(mExoPlayer, mStateBuilder, mMediaSession);
        mExoPlayer.addListener(playerEventListener);
    }

    private void play(Uri mediaUri) {
        String userAgent = Util.getUserAgent(getContext(), APPLICATION_NAME);
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    private void loadData(long recipeId, int position) {
        if(position == RECIPE_INGREDIENTS_KEY){
            mViewModel.getIngredients(recipeId).observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
                @Override
                public void onChanged(@Nullable List<Ingredient> ingredients) {
                    mIngredients = ingredients;
                    setAdapter();
                    showIngredients();
                }
            });
        }else{
            mViewModel.loadStepFromRecipe(position,recipeId).observe(getViewLifecycleOwner(), new Observer<Step>() {
                @Override
                public void onChanged(@Nullable Step step) {
                    mStep = step;
                    setStep();
                }
            });
        }
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecipe(recipeId,position - 1);
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecipe(recipeId,position + 1);
            }
        });
    }
}
