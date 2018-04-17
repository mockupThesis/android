package hu.webandmore.androidmocapclient.ui;

/***
 * Abstract presenter class
 * @param <S>
 */
public abstract class Presenter<S> {

    protected S screen;

    public void attachScreen(S screen){
        this.screen = screen;
    }

    public void detachScreen() {
        this.screen = null;
    }

}
