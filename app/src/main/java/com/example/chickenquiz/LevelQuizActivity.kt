package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chickenquiz.flappy.DonneesApp
import com.example.chickenquiz.flappy.GameActivity
import kotlin.properties.Delegates
import kotlin.random.Random


class LevelQuizActivity : AppCompatActivity() {

    //classe pour un niveau de l'appli

    companion object {
        private const val REQUEST_FLAPPY_GAME = 1
    }

    private lateinit var questionTextLayout: FrameLayout
    private lateinit var questionImageLayout: FrameLayout
    private lateinit var questionTextViewText: TextView
    private lateinit var questionTextViewImage: TextView
    private lateinit var scoretexte: TextView
    private var resID: Int = 0
    private var resIDim: Int = 0

    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var reessayer: Button
    private lateinit var arreter: Button
    private var score by Delegates.notNull<Int>()
    private var chance : Int = 0
    private var remainingLives: Int = 3
    private lateinit var gameManager: GameManager
    private lateinit var dbHelper: QuizDatabaseHelper

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vies : ImageView
    private var imagevie: Drawable? = null


    private lateinit var answerImageView1: ImageView
    private lateinit var answerImageView2: ImageView
    private lateinit var answerImageView3: ImageView
    private lateinit var answerImageView4: ImageView



    private var currentQuestionIndex: Int = 0
    private var currentLevel: Int = 1 // Niveau par défaut
    private var currentQuestionType: AnswerType = AnswerType.TEXT // Type de question par défaut
    private lateinit var questions: List<QuestionAnswerPair>

    val objectif: Int = Random.nextInt(3, 16)









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)


        gameManager = GameManager(sharedPreferences)
        dbHelper = QuizDatabaseHelper(this)


        chance = 1;

        // Récupération du niveau et du type de question depuis l'intent
        currentLevel = intent.getIntExtra("LEVEL", 1)
        currentQuestionType =
            intent.getSerializableExtra("QUESTION_TYPE") as? AnswerType ?: AnswerType.TEXT

        questions = dbHelper.getQuestionsForLevel(currentLevel)  //on récupère les questions du niveau dans la bdd
        imagevie = ContextCompat.getDrawable(this, R.drawable.troisvies)
        startMusic() //on lance la musique


        if(currentLevel == 1){
            val layoutNameText = "activity_question_algerie"
             resID = resources.getIdentifier(layoutNameText, "layout", packageName)

            val layoutNameImage = "activity_question_images"
            resIDim = resources.getIdentifier(layoutNameImage, "layout", packageName)


        }else if(currentLevel == 2){
            val layoutNameText = "activity_question_text_foot"
            resID = resources.getIdentifier(layoutNameText, "layout", packageName)

            val layoutNameImage = "activity_question_images"
            resIDim = resources.getIdentifier(layoutNameImage, "layout", packageName)
        }else if(currentLevel == 3){
        val layoutNameText = "activity_question"
        resID = resources.getIdentifier(layoutNameText, "layout", packageName)

        val layoutNameImage = "activity_question_images"
        resIDim = resources.getIdentifier(layoutNameImage, "layout", packageName)
    }else if(currentLevel == 4){
        val layoutNameText = "questionscapitale"
        resID = resources.getIdentifier(layoutNameText, "layout", packageName)

        val layoutNameImage = "activity_question_images"
        resIDim = resources.getIdentifier(layoutNameImage, "layout", packageName)
    }

        // Charger le layout en fonction du type de la premiere question du niveau
        when (currentQuestionType) {
            AnswerType.TEXT -> setContentView(resID)
            AnswerType.IMAGE -> setContentView(resIDim)
        }

        // Récupération des vues en fonction du layout chargé
        if (currentQuestionType == AnswerType.TEXT) {
            questionTextLayout = findViewById(R.id.questionTextLayout)
            questionTextViewText = findViewById(R.id.questionTextViewText)
            vies = findViewById(R.id.vies)
            vies.setImageDrawable(imagevie)

            answerButton1 = findViewById(R.id.answerButton1)
            answerButton2 = findViewById(R.id.answerButton2)
            answerButton3 = findViewById(R.id.answerButton3)
            answerButton4 = findViewById(R.id.answerButton4)

            // Ajout d'un écouteur de clic pour chaque bouton de réponse
            answerButton1.setOnClickListener { onAnswerButtonClick(it) }
            answerButton2.setOnClickListener { onAnswerButtonClick(it) }
            answerButton3.setOnClickListener { onAnswerButtonClick(it) }
            answerButton4.setOnClickListener { onAnswerButtonClick(it) }

        } else {
            questionImageLayout = findViewById(R.id.questionImageLayout)
            questionTextViewImage = findViewById(R.id.questionTextViewImage)
            vies = findViewById(R.id.vies)

            answerImageView1 = findViewById(R.id.answerImageView1)
            answerImageView2 = findViewById(R.id.answerImageView2)
            answerImageView3 = findViewById(R.id.answerImageView3)
            answerImageView4 = findViewById(R.id.answerImageView4)

            // Ajout d'un écouteur de clic pour chaque bouton de réponse
            answerImageView1.setOnClickListener { onAnswerImageClick(it) }
            answerImageView2.setOnClickListener { onAnswerImageClick(it) }
            answerImageView3.setOnClickListener { onAnswerImageClick(it) }
            answerImageView4.setOnClickListener { onAnswerImageClick(it) }
        }



        // Chargement et affichage de la première question
        loadNextQuestion()


    }


    private fun loadNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            displayQuestion(question)
        } else {
            unlockNextLevel()
            val intent = Intent(this, ReussiActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayQuestion(question: QuestionAnswerPair?) {
        question?.let {
            if (question.answerType == AnswerType.TEXT) {
                setContentView(resID)
                vies = findViewById(R.id.vies)
                vies.setImageDrawable(imagevie)
                questionTextViewText = findViewById(R.id.questionTextViewText)
                answerButton1 = findViewById(R.id.answerButton1)
                answerButton2 = findViewById(R.id.answerButton2)
                answerButton3 = findViewById(R.id.answerButton3)
                answerButton4 = findViewById(R.id.answerButton4)

                answerButton1.setOnClickListener { onAnswerButtonClick(it) }
                answerButton2.setOnClickListener { onAnswerButtonClick(it) }
                answerButton3.setOnClickListener { onAnswerButtonClick(it) }
                answerButton4.setOnClickListener { onAnswerButtonClick(it) }

                questionTextViewText.text = it.question
                answerButton1.text = it.answers[0]
                answerButton2.text = it.answers[1]
                answerButton3.text = it.answers[2]
                answerButton4.text = it.answers[3]
            } else {
                setContentView(resIDim)
                vies = findViewById(R.id.vies)
                vies.setImageDrawable(imagevie)

                questionTextViewImage = findViewById(R.id.questionTextViewImage)
                questionTextViewImage.text = it.question

                answerImageView1 = findViewById(R.id.answerImageView1)
                answerImageView2 = findViewById(R.id.answerImageView2)
                answerImageView3 = findViewById(R.id.answerImageView3)
                answerImageView4 = findViewById(R.id.answerImageView4)

                // Ajout d'un écouteur de clic pour chaque bouton de réponse
                answerImageView1.setOnClickListener { onAnswerImageClick(it) }
                answerImageView2.setOnClickListener { onAnswerImageClick(it) }
                answerImageView3.setOnClickListener { onAnswerImageClick(it) }
                answerImageView4.setOnClickListener { onAnswerImageClick(it) }

                answerImageView1.setImageResource(resources.getIdentifier(it.answers[0], "drawable", packageName))
                answerImageView2.setImageResource(resources.getIdentifier(it.answers[1], "drawable", packageName))
                answerImageView3.setImageResource(resources.getIdentifier(it.answers[2], "drawable", packageName))
                answerImageView4.setImageResource(resources.getIdentifier(it.answers[3], "drawable", packageName))
            }
        }

    }






    private fun onAnswerButtonClick(view: View) {
        val selectedAnswerIndex = when (view.id) {
            R.id.answerButton1 -> 0
            R.id.answerButton2 -> 1
            R.id.answerButton3 -> 2
            R.id.answerButton4 -> 3
            else -> -1
        }

        val currentQuestion = questions[currentQuestionIndex]
        if (currentQuestion != null && selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
            if(currentLevel == 1){

                showAlgerieMessage()
            }
            currentQuestionIndex++
            android.os.Handler().postDelayed({
                loadNextQuestion()
                                             }, 1200)

            // La réponse est correcte
        } else {
            // La réponse est incorrecte, on baisse la vie
            remainingLives--
            updateHearts()
            // Afficher l'écran d'erreur pendant 3 secondes
            if(remainingLives > 0) showErrorMessage()
        }
    }

    private fun onAnswerImageClick(view: View) {
        val selectedAnswerIndex = when (view.id) {
            R.id.answerImageView1 -> 0
            R.id.answerImageView2 -> 1
            R.id.answerImageView3 -> 2
            R.id.answerImageView4 -> 3
            else -> -1
        }

        val currentQuestion = questions.get(currentQuestionIndex)
        if (currentQuestion != null && selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
            currentQuestionIndex++
            loadNextQuestion()
            // La réponse est correcte
        } else {
            // La réponse est incorrecte
            remainingLives--
            updateHearts()
            // Afficher l'écran d'erreur pendant 3 secondes
            if(remainingLives > 0) showErrorMessage()
        }
    }

    private fun updateHearts() {
        when (remainingLives) {
            2 -> {
                imagevie = ContextCompat.getDrawable(this, R.drawable.deuxvies)
                vies.setImageDrawable(imagevie)
            }
            1 -> {
                imagevie = ContextCompat.getDrawable(this, R.drawable.unevie)
                vies.setImageDrawable(imagevie)
            }
            else -> {

                if(chance == 1) {
                    chance --;
                    setContentView(R.layout.revivre)
                    scoretexte = findViewById(R.id.scoretexte)
                    scoretexte.setText(objectif.toString())
                    reessayer = findViewById(R.id.play)
                    arreter = findViewById(R.id.stopper)
                    reessayer.setOnClickListener {
                        val flappyIntent = Intent(this@LevelQuizActivity, GameActivity::class.java)
                        startActivityForResult(flappyIntent, REQUEST_FLAPPY_GAME)
                        displayQuestion(questions[currentQuestionIndex])
                        remainingLives++
                    }
                    arreter.setOnClickListener { finish() }

                }else{
                    val lostintent = Intent(this@LevelQuizActivity, LostActivity::class.java)
                    startActivity(lostintent)
                    finish()
                }
            }
        }
    }

    private fun unlockNextLevel() {
        // Vérifier si le niveau actuel est le suivant du dernier niveau terminé
        //cela evite que tout les niveaux se débloquent en finisant le premier à la chaine
        if (currentLevel == gameManager.lastCompletedLevel + 1) {
            // Augmenter le niveau maximal déverrouillé de 1
            if(gameManager.maxUnlockedLevel < 4){
            gameManager.maxUnlockedLevel++
            gameManager.lastCompletedLevel = currentLevel // Mettre à jour le dernier niveau terminé
            Toast.makeText(this@LevelQuizActivity, "vous avez débloqué " + gameManager.maxUnlockedLevel, Toast.LENGTH_SHORT).show()

            // Enregistrer les nouvelles valeurs dans les préférences
            gameManager.saveMaxUnlockedLevel()
            gameManager.saveLastCompletedLevel()
        }}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FLAPPY_GAME) {
            if (resultCode == RESULT_OK) {

                // Obtenez le score à partir de l'intent de résultat
                 score = data?.getIntExtra("SCORE", -1) ?: -1

                // Comparez le score avec scorfe
                if (score < objectif) { //j'ai mis un pour test que la logique marche mais sinon ça serat la valeur aleatoire
                   showDefaiteMessage()
                    finish()
                }
            }
        }
    }


    private fun showErrorMessage() {
        val errorIntent = Intent(this, ErrorScreenActivity::class.java)
        startActivity(errorIntent)
    }

    private fun showAlgerieMessage() {
        val intent = Intent(this, AlgerieActivity::class.java)
        startActivity(intent)
    }

    private fun showDefaiteMessage() {
        val errorIntent = Intent(this, LostActivity::class.java)
        startActivity(errorIntent)
    }

    private fun startMusic() {
        if (!::mediaPlayer.isInitialized || !mediaPlayer.isPlaying) {
            if(currentLevel == 1){
                DonneesApp.mp = MediaPlayer.create(this, R.raw.rayah).apply { isLooping = true }
                DonneesApp.mp.start()
            }else if (currentLevel == 4){
            DonneesApp.mp = MediaPlayer.create(this, R.raw.pirate).apply { isLooping = true }
            DonneesApp.mp.start()}else if(currentLevel == 2){
                DonneesApp.mp = MediaPlayer.create(this, R.raw.footmusiq).apply { isLooping = true }
                DonneesApp.mp.start()

            }else{
                DonneesApp.mp = MediaPlayer.create(this, R.raw.musique).apply { isLooping = true }
                DonneesApp.mp.start()
            }

            // volume du tel au maximum
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            audioManager?.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0
            )

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }



    fun stopMusic() { // Arrêter la musique à l'arrêt du jeu
        DonneesApp.mp.stop()
    }




}