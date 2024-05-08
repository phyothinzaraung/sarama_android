package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.QuestionAnswerAdapter;
import com.koekoetech.sayarma.custom_control.MyanBoldTextView;
import com.koekoetech.sayarma.custom_control.MyanButton;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.DialogHelperWithOkCancelButton;
import com.koekoetech.sayarma.helper.ExternalFileHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.interfaces.AnswerSelectionCallback;
import com.koekoetech.sayarma.model.AnswerModel;
import com.koekoetech.sayarma.model.AnswersViewModel;
import com.koekoetech.sayarma.model.QuestionModel;
import com.koekoetech.sayarma.model.ScoreModel;
import java.io.File;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class QuestionAnswerActivity extends BaseActivity implements AnswerSelectionCallback {


    public static String TYPE_EXTRA = "TYPE";
    public static String LEVEL_ID_OR_SUB_CHAPTER_ID_EXTRA = "LEVEL_OR_SUB_CHAPTER_EXTRA";

    private int questionType = 0;
    private String levelOrSubChapterId = "";

    private RelativeLayout relative_result;
    private MyanTextView tv_question;
    private MyanTextView tv_total_marks;
    private CardView cv_result_message;
    private MyanButton btn_finish;
    private MyanBoldTextView label_hint;
    private MyanTextView text_hint;
    private RecyclerView recyclerview;
    private MyanButton btn_answer;
    private MyanButton btn_next;
    private SeekBar sb_break_point;
    private MyanTextView txtAnsRemark;
    private MyanTextView txtPointRemark;
    private AppCompatImageView imgQuestionQuizSound;
    private AppCompatImageView imgQuestionQuizSoundMute;
    private AppCompatImageView imgAnswerQuizSound;
    private AppCompatImageView imgAnswerQuizSoundMute;

    private QuestionAnswerAdapter questionAnswerAdapter;
    private ArrayList<QuestionModel> questionList;
    private int questionIndex = 0;

    private AnswersViewModel selectedAnswer = null;

    private Handler handler;

    private int totalMark = 0;

    private ProgressDialog progressDialog;
    DialogHelperWithOkCancelButton dialogHelperWithOkCancelButton;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Realm realm;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    private void bindViews(){
        relative_result = findViewById(R.id.relative_result);
        tv_question = findViewById(R.id.tv_question);
        tv_total_marks = findViewById(R.id.tv_total_marks);
        cv_result_message = findViewById(R.id.cv_result_message);
        btn_finish = findViewById(R.id.btn_finish);
        label_hint = findViewById(R.id.label_hint);
        text_hint = findViewById(R.id.text_hint);
        recyclerview = findViewById(R.id.recyclerview);
        btn_answer = findViewById(R.id.btn_answer);
        btn_next = findViewById(R.id.btn_next);
        sb_break_point = findViewById(R.id.sb_break_point);
        txtAnsRemark = findViewById(R.id.txtAnsRemark);
        txtPointRemark = findViewById(R.id.txtPointRemark);
        imgQuestionQuizSound = findViewById(R.id.imgQuestionQuizSound);
        imgQuestionQuizSoundMute = findViewById(R.id.imgQuestionQuizSoundMute);
        imgAnswerQuizSound = findViewById(R.id.imgAnswerQuizSound);
        imgAnswerQuizSoundMute = findViewById(R.id.imgAnswerQuizSoundMute);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_question_answer;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        bindViews();
        init();
        getQuestionList();

        sb_break_point.setMax(getTotalMask());
        btn_answer.setOnClickListener(v -> {
            if(mediaPlayer1 != null){
                mediaPlayer1.release();
            }
            if (selectedAnswer != null)
                showResultMessage();
        });

    }

    private void init()
    {
        handler = new Handler();

        setUpToolbar(true);
        setUpToolbarText("");

        questionType = getIntent().getIntExtra(TYPE_EXTRA, 0);
        levelOrSubChapterId = getIntent().getStringExtra(LEVEL_ID_OR_SUB_CHAPTER_ID_EXTRA);

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(QuestionAnswerActivity.this);

        realm = Realm.getDefaultInstance();

        dialogHelperWithOkCancelButton = new DialogHelperWithOkCancelButton(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");

        btn_answer.setMyanmarText(TextDictionaryHelper.getText(this.getApplicationContext(), TextDictionaryHelper.TEXT_BUTTON_ANSWER));

        btn_finish.setMyanmarText(TextDictionaryHelper.getText(this.getApplicationContext(), TextDictionaryHelper.TEXT_BUTTON_FINISH));
        label_hint.setMyanmarText(TextDictionaryHelper.getText(this.getApplicationContext(), TextDictionaryHelper.TEXT_LABEL_HINT));

        questionList = new ArrayList<>();
        questionAnswerAdapter = new QuestionAnswerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(questionAnswerAdapter);
        recyclerview.setHasFixedSize(true);
    }

    private void getQuestionList() {

        String levelorchapter = getChapterOrSubChapter();

        RealmResults<QuestionModel> results = realm
                .where(QuestionModel.class)
                .equalTo("Type", questionType)
                .equalTo(levelorchapter, levelOrSubChapterId)
                .sort("OrderingID", Sort.ASCENDING).findAll();

        questionList.addAll(results);

        bindQuestionAnswer();
    }

    private String getChapterOrSubChapter() {

        if(questionType == 1){
            //LevelID
            return "levelID";
        }
        else {
            return "SubChapterID";
        }

    }

    private int getTotalMask() {
        return realm.where(QuestionModel.class).equalTo(getChapterOrSubChapter(), levelOrSubChapterId).equalTo("Type", questionType).sum("Point").intValue();
    }

    private void bindQuestionAnswer() {
        QuestionModel question = questionList.get(questionIndex);

        questionAnswerAdapter.clear();
        tv_question.setMyanmarText(question.getQuestionContent());

        playQuestionMp3(question.getAudio1());
        playAnswerMp3(question.getAudio2());

        RealmResults<AnswerModel> answerList = realm
                .where(AnswerModel.class)
                .equalTo("QuestionId", question.getQuestionId())
                .sort("OrderingID", Sort.ASCENDING)
                .findAll();


        for (int i = 0; i < answerList.size(); i++) {

            AnswerModel answersModel = answerList.get(i);

            AnswersViewModel model = new AnswersViewModel();
            model.setAnswerId(answersModel.getAnswerId());
            model.setQuestionId(answersModel.getQuestionId());
            model.setAnswer(answersModel.getAnswer());
            model.setPoint(answersModel.getPoint());
            model.setRight(answersModel.isRight());
            model.setTips(answersModel.getTips());
            model.setSelected(false);
            questionAnswerAdapter.add(model);
        }

        progressDialog.show();
        handler.postDelayed(() -> {
            questionAnswerAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }, 500);

    }

    private void nextQuestion() {
        if (questionIndex != (questionList.size() - 1)) {

            imgQuestionQuizSound.setVisibility(View.VISIBLE);
            imgQuestionQuizSoundMute.setVisibility(View.GONE);

            questionIndex++;
            bindQuestionAnswer();

        } else {
            questionAnswerAdapter.clear();

            //End question
            showTotalMark();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void select(int position, AnswersViewModel model) {

        btn_answer.setEnabled(true);
        selectedAnswer = model;

        questionAnswerAdapter.replace(position, model);
        questionAnswerAdapter.notifyDataSetChanged();

        for (int i = 0; i < questionAnswerAdapter.getItemCount(); i++) {
            if (i != position) {
                AnswersViewModel answersViewModel =
                        (AnswersViewModel) questionAnswerAdapter
                                .getItemsList().get(i);
                answersViewModel.setSelected(false);
                questionAnswerAdapter.replace(i, answersViewModel);
            }
        }
        questionAnswerAdapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void showResultMessage() {

        if (selectedAnswer != null) {

            if (selectedAnswer.isSelected()) {

                progressDialog.show();
                handler.postDelayed(() -> {
                    questionAnswerAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }, 400);

                recyclerview.setVisibility(View.INVISIBLE);
                cv_result_message.setVisibility(View.VISIBLE);
                text_hint.setVisibility(View.VISIBLE);
                text_hint.setMyanmarText(selectedAnswer.getTips());
                totalMark += selectedAnswer.getPoint();
                sb_break_point.setProgress(totalMark);
                imgQuestionQuizSound.setVisibility(View.GONE);
                imgQuestionQuizSoundMute.setVisibility(View.GONE);
                imgAnswerQuizSound.setVisibility(View.VISIBLE);
                imgAnswerQuizSoundMute.setVisibility(View.GONE);

                if(selectedAnswer.isRight()){
                    txtAnsRemark.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_ANSWER_RIGHT));
                    txtPointRemark.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_POINT_RIGHT));
                }else {
                    txtAnsRemark.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_ANSWER_WRONG));
                    txtPointRemark.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_POINT_WRONG));
                }

                btn_answer.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);

                btn_next.setOnClickListener(v -> {

                    if(mediaPlayer2 != null){
                        mediaPlayer2.release();
                    }

                    recyclerview.setVisibility(View.VISIBLE);
                    cv_result_message.setVisibility(View.INVISIBLE);
                    btn_next.setVisibility(View.GONE);
                    btn_answer.setVisibility(View.VISIBLE);
                    btn_answer.setEnabled(false);
                    nextQuestion();
                });

                selectedAnswer = null;
            }

        }

    }

    private void playQuestionMp3(String audio) {
        mediaPlayer1 = new MediaPlayer();
        try {
            File file = ExternalFileHelper.getQuizAudioFile(getApplicationContext(), audio);
            mediaPlayer1.setDataSource(file.getAbsolutePath());
            mediaPlayer1.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgQuestionQuizSound.setOnClickListener(v -> {
            mediaPlayer1.start();

            imgQuestionQuizSound.setVisibility(View.GONE);
            imgQuestionQuizSoundMute.setVisibility(View.VISIBLE);
        });

        imgQuestionQuizSoundMute.setOnClickListener(v -> {
            mediaPlayer1.pause();

            imgQuestionQuizSound.setVisibility(View.VISIBLE);
            imgQuestionQuizSoundMute.setVisibility(View.GONE);
        });

        mediaPlayer1.setOnCompletionListener(mp -> {

            imgQuestionQuizSound.setVisibility(View.VISIBLE);
            imgQuestionQuizSoundMute.setVisibility(View.GONE);
        });

    }

    private void playAnswerMp3(String audio) {
        mediaPlayer2 = new MediaPlayer();

        try {
            File file = ExternalFileHelper.getQuizAudioFile(getApplicationContext(), audio);
            mediaPlayer2.setDataSource(file.getAbsolutePath());
            mediaPlayer2.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgAnswerQuizSound.setOnClickListener(v -> {
            mediaPlayer2.start();
            imgAnswerQuizSound.setVisibility(View.GONE);
            imgAnswerQuizSoundMute.setVisibility(View.VISIBLE);
        });

        imgAnswerQuizSoundMute.setOnClickListener(v -> {
            mediaPlayer2.pause();
            imgAnswerQuizSound.setVisibility(View.VISIBLE);
            imgAnswerQuizSoundMute.setVisibility(View.GONE);
        });

        mediaPlayer2.setOnCompletionListener(mp -> {

            imgAnswerQuizSound.setVisibility(View.VISIBLE);
            imgAnswerQuizSoundMute.setVisibility(View.GONE);
        });

    }

    private void showTotalMark() {
        relative_result.setVisibility(View.VISIBLE);

        tv_total_marks.setMyanmarText(TextDictionaryHelper.getText(this.getApplicationContext(),
                TextDictionaryHelper.TEXT_TOTAL_MARKS) + totalMark + "/" + getTotalMask() +
                TextDictionaryHelper.getText(this.getApplicationContext(), TextDictionaryHelper.TEXT_POINTS));

        realm.executeTransactionAsync(realm -> {
            ScoreModel model = new ScoreModel();
            model.setLessonID(levelOrSubChapterId);
            model.setType(questionType+"");
            model.setScorePoint(totalMark);
            model.setUserID(sharedPreferenceHelper.getUserId());
            model.setSync(false);

            realm.insertOrUpdate(model);
        });


        btn_finish.setOnClickListener(v -> finish());

    }

    @Override
    public void onBackPressed() {

        dialogHelperWithOkCancelButton.showDialog(R.mipmap.app_icon,
                "သေချာပါသလား", "ထွက်မည်",
                "မထွက်ပါ");

        dialogHelperWithOkCancelButton.getBtnOk().
                setOnClickListener(v -> {
                    if(mediaPlayer1 != null){
                        mediaPlayer1.release();
                    }
                    if (mediaPlayer2 != null){
                        mediaPlayer2.release();
                    }
                    QuestionAnswerActivity.super.onBackPressed();
                });

        dialogHelperWithOkCancelButton.getBtnCancel()
                .setOnClickListener(v -> dialogHelperWithOkCancelButton.hideDialog());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer1 != null) {
            mediaPlayer1.release();
        }
        if (mediaPlayer2 != null){
            mediaPlayer2.release();
        }
    }
}
