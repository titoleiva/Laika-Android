package social.laika.app.models.indexes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import social.laika.app.R;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class FreeTime {

    public int mIndex;
    public String mName;

    public FreeTime(int mIndex, String mName) {
        this.mIndex = mIndex;
        this.mName = mName;
    }

    public static List<FreeTime> getFreeTimes(Context context) {

        List<FreeTime> freeTimes = new ArrayList<>();
        String[] names = context.getResources().getStringArray(R.array.free_time_adopt);

        FreeTime zero = new FreeTime(Tag.TIME_ZERO, names[Tag.TIME_ZERO]);
        FreeTime min = new FreeTime(Tag.TIME_MIN, names[Tag.TIME_MIN]);
        FreeTime normal = new FreeTime(Tag.TIME_NORMAL, names[Tag.TIME_NORMAL]);
        FreeTime great = new FreeTime(Tag.TIME_GREAT, names[Tag.TIME_GREAT]);

        freeTimes.add(zero);
        freeTimes.add(min);
        freeTimes.add(normal);
        freeTimes.add(great);

        return freeTimes;

    }
}
