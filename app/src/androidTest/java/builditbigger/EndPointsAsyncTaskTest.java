package builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.EndPointsAsyncTask;
import com.udacity.gradle.builditbigger.LiveDataResource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;

import static com.google.android.gms.internal.zzahn.runOnUiThread;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EndPointsAsyncTaskTest {

    LiveDataResource<String> mJokeLiveDataString = null;
    CountDownLatch signal = null;

    @Test
    public void testAlbumGetTask() throws Throwable {
        signal = new CountDownLatch(1);
        final EndPointsAsyncTask task = new EndPointsAsyncTask();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                task.setListener(new EndPointsAsyncTask.JokeGetTaskListener() {
                    @Override
                    public void onComplete(LiveDataResource jokeLiveDataResource) {
                        mJokeLiveDataString = jokeLiveDataResource;
                        signal.countDown();
                    }
                }).execute();
            }
        });

        signal.await();

        assertNull(mJokeLiveDataString.errorMessage);
        assertFalse(TextUtils.isEmpty(mJokeLiveDataString.data));
        assertTrue(mJokeLiveDataString.data.startsWith("This"));
        assertTrue(mJokeLiveDataString.data.endsWith("joke"));

    }
}
