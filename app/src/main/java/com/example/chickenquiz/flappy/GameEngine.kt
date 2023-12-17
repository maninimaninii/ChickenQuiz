package com.example.chickenquiz.flappy

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Canvas
import android.widget.Toast
import com.example.chickenquiz.flappy.SharedData.Companion.attente
import com.example.chickenquiz.flappy.SharedData.Companion.etat
import java.util.*

class GameEngine { //toutes les fonctions de dessin, d'affichage...
    var background: BackgroundImage
    var wazo: Wazo
    var tuyos: ArrayList<Tuyau> //les tuyeaux
    var separation: Int = 0
    var nombre: Int = 0 //distance qui sépare les tuyaux
    var score: Int
    // 0 = pas commencé, 1 = en cours, 2 == fini

    init {
        score = 0
        etat = 0
        background = BackgroundImage()
        wazo = Wazo(TypeOiseau.POULET)
        tuyos = ArrayList()
        inituyo()
    }

    fun inituyo() {  //initiation de la liste des tuyeaux
        this.separation = (300 * DonneesApp.SCREEN_HEIGHT / 1920) //on assigne a la separation sa valeur
        nombre = 6
        for (i in 0 until 6) {
            if (i < 3) { //guyau du haut
                tuyos.add(Tuyau(DonneesApp.SCREEN_WIDTH + i * ((DonneesApp.SCREEN_WIDTH + 200 * DonneesApp.SCREEN_WIDTH / 1080) / 3), 0))
                DonneesApp.bitmapBank.tuyaux[1]?.let { tuyos[i].setbm(it) }
                tuyos[tuyos.size - 1].spawnAleatoire()
            } else {
                //tuyau du bas dans la hauteur est determinee en fonction de celui du haut et de la separation
                tuyos.add(
                    Tuyau(
                        tuyos[i - nombre / 2].getTuyauX(),
                        tuyos[i - nombre / 2].getTuyauY() + DonneesApp.bitmapBank.gettuyoHeigth() + separation
                    )
                )
                DonneesApp.bitmapBank.tuyaux[0]?.let { tuyos[i].setbm(it) }
            }
        }
    }

    fun DessinBg(c: Canvas) {
        DonneesApp.bitmapBank.getBackground()?.let {
            c.drawBitmap(
                it,
                background.getX().toFloat(),
                background.getY().toFloat(),
                null
            )
        }

        if (background.getX() <= DonneesApp.bitmapBank.getBackgroundWidth() - DonneesApp.SCREEN_WIDTH) {
            DonneesApp.bitmapBank.getBackground()?.let {
                c.drawBitmap(
                    it,
                    (background.getX() + DonneesApp.bitmapBank.getBackgroundWidth()).toFloat(),
                    background.getY().toFloat(),
                    null
                )
            }
        }
    }


    fun DessinOiseau(canvas: Canvas) { // afficher l'oiseau sur l'ecran et veiller a son integrite
        if (wazo.type == TypeOiseau.WAZO) {
            var debut = 0 //variables pour controller la couleur de l'oiseau affiché
            var fin = 3
            if (etat == 1) { //jeu débuté
                if (wazo.getWazoY() < (DonneesApp.SCREEN_HEIGHT - DonneesApp.bitmapBank.getwazoHeight()) || wazo.getVitesse() < 0) { //chute de l'oiseau
                    wazo.setVitesse(wazo.getVitesse() + DonneesApp.gravite)
                    wazo.setY(wazo.getWazoY() + wazo.getVitesse())//loiseau chute
                }
            }

            var frame = wazo.getFrame() //on recupere l'image a afficher

            DonneesApp.bitmapBank.getBird(frame)?.let {
                canvas.drawBitmap(
                    it,
                    wazo.getWazoX().toFloat(),
                    wazo.getWazoY().toFloat(),
                    null
                )
            } //on dessine l'ioseau

            // changement de couleur en fonction du score
            frame++ // on incremente pour changer la frame de l'oiseau et creer le mouvement

            // changement de couleur en fonction du score
            if (score % 30 <= 10) { //si le score est 10, 40, 70... on affiche le premier oiseau
                debut = 0
                fin = 3
            } else {
                if (score % 30 <= 20) { //20,50,80...
                    debut = 4
                    fin = 7
                } else { //30,60,90..
                    debut = 8
                    fin = 11
                }
            }

            if (frame >= fin) { // si le mouvement complet a ete affiché
                frame = debut
            }

            wazo.setFrame(frame)

        } else {

            var debut = 0 //variables pour controller la couleur de l'oiseau affiché
            var fin = 2
            if (etat == 1) { //jeu débuté
                if (wazo.getWazoY() < (DonneesApp.SCREEN_HEIGHT - DonneesApp.bitmapBank.getwazoHeight()) || wazo.getVitesse() < 0) { //chute de l'oiseau
                    wazo.setVitesse(wazo.getVitesse() + DonneesApp.gravite)
                    wazo.setY(wazo.getWazoY() + wazo.getVitesse())//loiseau chute
                }
            }

            var frame = wazo.getFrame() //on recupere l'image a afficher

            DonneesApp.bitmapBank.getPoulet(frame)?.let {
                canvas.drawBitmap(
                    it,
                    wazo.getWazoX().toFloat(),
                    wazo.getWazoY().toFloat(),
                    null
                )
            } //on dessine l'ioseau

            // changement de couleur en fonction du score
            frame++ // on incremente pour changer la frame de l'oiseau et creer le mouvement

            if (frame >= fin) { // si le mouvement complet a ete affiché
                frame = debut
            }

            wazo.setFrame(frame)


        }
    }

    fun DessinTuyaux(canvas: Canvas) {
        if (etat == 1) {
            for (i in 0 until nombre) {
                //conditions d'arret

                if (wazo.getOiseauRect().intersect(tuyos[i].getRect()) || tuyos[i].getRect().intersect(wazo.getOiseauRect())) { //collision
                    etat = 2 //jeu fini
                    DonneesApp.stopmusc()
                    val context = DonneesApp.gameacContext
                    DonneesApp.gv.stopthread()

                    if (context is Activity) {
                        val resultIntent = Intent()
                        resultIntent.putExtra("SCORE", score)

                        // Définissez le résultat et terminez l'activité
                        context.setResult(RESULT_OK, resultIntent)
                        context.finish()
                    }
                }


                // si l'oiseau dépasse un tuyeau le score augmente
                if (wazo.getWazoX() + DonneesApp.bitmapBank.getwazoWidth() > tuyos[i].getTuyauX() + DonneesApp.bitmapBank.gettuyoWidth() / 2 //deuxième condition car ça ajoutait plus que nécessaire au score
                    && wazo.getWazoX() + DonneesApp.bitmapBank.getwazoWidth() <= tuyos[i].getTuyauX() + DonneesApp.bitmapBank.gettuyoWidth() / 2 + Tuyau.vitesse && i < 3
                ) {
                    score++
                }
                if (tuyos[i].getTuyauX() < -DonneesApp.bitmapBank.gettuyoWidth()) { //tuyau arrivé en fin d'écran
                    tuyos[i].setTuyauX(DonneesApp.SCREEN_WIDTH)  //on le remet au debut
                    if (i < nombre / 2) { //tyuyau du haut
                        tuyos[i].spawnAleatoire() //hauteur aleatoire
                    } else { //bas
                        tuyos[i].setTuyauY(tuyos[i - nombre / 2].getTuyauY() + DonneesApp.bitmapBank.gettuyoHeigth() + separation)
                    }
                }
                tuyos[i].draw(canvas)
            }
        }
    }

    fun DessinScore(canvas: Canvas) {

        if (this.score < 10) { // score petit un seul nombre a afficher
            DonneesApp.bitmapBank.getscore(this.score)?.let {
                canvas.drawBitmap(
                    it,
                    DonneesApp.SCREEN_WIDTH / 2.toFloat(),
                    (DonneesApp.SCREEN_HEIGHT - 300).toFloat(),
                    null
                )
            }
        } else { //sinon deux nombres a afficher
            DonneesApp.bitmapBank.getscore(this.score / 10)?.let {
                canvas.drawBitmap(
                    it,
                    (DonneesApp.SCREEN_WIDTH / 2 - 25).toFloat(),
                    (DonneesApp.SCREEN_HEIGHT - 300).toFloat(),
                    null
                )
            }
            DonneesApp.bitmapBank.getscore(this.score % 10)?.let {
                canvas.drawBitmap(
                    it,
                    (DonneesApp.SCREEN_WIDTH / 2 + 25).toFloat(),
                    (DonneesApp.SCREEN_HEIGHT - 300).toFloat(),
                    null
                )
            }
        }
    }
}
