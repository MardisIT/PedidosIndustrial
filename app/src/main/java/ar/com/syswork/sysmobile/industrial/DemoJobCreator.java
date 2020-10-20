package ar.com.syswork.sysmobile.industrial;

import android.content.Context;

//import androidx.annotation.NonNull;

import androidx.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

import javax.annotation.Nullable;

public class DemoJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case ServerPollingJob.TAG:
                return new ServerPollingJob();
            default:
                return null;
        }
    }
    public static final class AddReceiver extends AddJobCreatorReceiver {
        @Override
        protected void addJobCreator(@NonNull Context context, @NonNull JobManager manager) {
            // manager.addJobCreator(new DemoJobCreator());
        }
    }
}
