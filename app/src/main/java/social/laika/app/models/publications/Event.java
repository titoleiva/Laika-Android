package social.laika.app.models.publications;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;
import social.laika.app.utils.Do;
import social.laika.app.utils.Flurry;

@Table(name = Event.TABLE_NAME)
public class Event extends BasePublication {

    public final static String TABLE_NAME = "events";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_SPONSOR_ID = "sponsor_id";
    public final static String COLUMN_SPONSOR_NAME = "sponsor_name";
    public final static String COLUMN_CITY_ID = "city_id";
    public final static String COLUMN_ADDRESS = "address";
    public final static String COLUMN_START_DATE = "start_date";
    public final static String COLUMN_FINISH_DATE = "finish_date";
    public final static String COLUMN_START_TIME = "start_time";
    public final static String COLUMN_FINISH_TIME = "finish_time";

    public final static String API_EVENTS = "events";
    public final static String API_EVENT_ID = "event_id";
    public final static String API_URL_EVENT = "url_event";
    public final static String API_ID = "id";
    public final static String API_LAST_EVENT_ID = "last_event_id";
    public final static String API_LIMIT = "limit";

    @Column(name = COLUMN_SERVER_ID)
    public int mServerId;

    @Column(name = COLUMN_NAME)
    public String mName;

    @Column(name = COLUMN_SPONSOR_ID)
    public int mSponsorId;

    @Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsorName;

    @Column(name = API_URL_EVENT)
    public String mUrlEvent;

    @Column(name = COLUMN_CITY_ID)
    public int mCityId;

    @Column(name = COLUMN_ADDRESS)
    public String mAddress;

    @Column(name = COLUMN_START_DATE)
    public String mStartDate;

    @Column(name = COLUMN_FINISH_DATE)
    public String mFinishDate;

    @Column(name = COLUMN_START_TIME)
    public String mStartTime;

    @Column(name = COLUMN_FINISH_TIME)
    public String mFinishTime;

    public Event() {
        super();
    }


    public Event(int mEventId, String mName, int mSponsorId, String mSponsorName,
                 String mUrlImage, String mUrlEvent, int mCityId, String mStartDate,
                 String mFinishDate, String mStartTime, String mFinishTime, boolean mIsPaid) {
        super();
        this.mServerId = mEventId;
        this.mName = mName;
        this.mSponsorId = mSponsorId;
        this.mSponsorName = mSponsorName;
        this.mUrlImage = mUrlImage;
        this.mUrl = mUrlEvent;
        this.mCityId = mCityId;
        this.mStartDate = mStartDate;
        this.mFinishDate = mFinishDate;
        this.mStartTime = mStartTime;
        this.mFinishTime = mFinishTime;
        this.mIsPaid = mIsPaid;
    }

    public Event(JSONObject jsonObject) {
        super();
        this.mServerId = jsonObject.optInt(API_ID);
        this.mName = jsonObject.optString(COLUMN_NAME);
        this.mSponsorId = jsonObject.optInt(COLUMN_SPONSOR_ID);
        this.mSponsorName = jsonObject.optString(COLUMN_SPONSOR_NAME);
        this.mUrlImage = jsonObject.optString(COLUMN_URL_IMAGE);
        this.mUrl = jsonObject.optString(API_URL_EVENT);
        this.mCityId = jsonObject.optInt(COLUMN_CITY_ID, 1);
        this.mAddress = jsonObject.optString(COLUMN_ADDRESS);
        this.mStartDate = jsonObject.optString(COLUMN_START_DATE);
        this.mFinishDate = jsonObject.optString(COLUMN_FINISH_DATE);
        this.mStartTime = jsonObject.optString(COLUMN_START_TIME);
        this.mFinishTime = jsonObject.optString(COLUMN_FINISH_TIME);
        this.mIsPaid = jsonObject.optBoolean(COLUMN_IS_PAID);

    }


    public String getDate() {

        if (Do.isNullOrEmpty(mFinishDate) || mStartDate.equals(mFinishDate)) {
            return mStartDate;

        } else {
            return "del " + mStartDate + " al " + mFinishDate;
        }
    }

    public String getTime() {
        return mStartTime + " - " + mFinishTime;

    }

    public void update(Event event) {

        this.mServerId = event.mServerId;
        this.mName = event.mName;
        this.mSponsorId = event.mSponsorId;
        this.mSponsorName = event.mSponsorName;
        this.mUrlImage = event.mUrlImage;
        this.mUrl = event.mUrl;
        this.mCityId = event.mCityId;
        this.mAddress = event.mAddress;
        this.mStartDate = event.mStartDate;
        this.mFinishDate = event.mFinishDate;
        this.mStartTime = event.mStartTime;
        this.mFinishTime = event.mFinishTime;
        this.mIsPaid = event.mIsPaid;

        this.save();

    }

    // DataBase Methods

    public static void saveEvents(JSONObject jsonObject) {

        try {

            JSONArray jsonEvents = jsonObject.getJSONArray(API_EVENTS);

            for (int i = 0; i < jsonEvents.length(); i++) {

                JSONObject jsonEvent = jsonEvents.getJSONObject(i);
                Event event = new Event(jsonEvent);

                createOrUpdate(event);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void createOrUpdate(Event event) {

        if (!Event.isSaved(event)) {
            event.save();

        } else {
            Event oldEvent = getSingleEvent(event);
            oldEvent.update(event);

        }
    }

    public static List<Event> getEvents() {

        String order = COLUMN_SERVER_ID + DB.DESC;
        return new Select().from(Event.class).orderBy(order).execute();

    }

    public static List<Event> getFavoriteEvents() {

        String condition = COLUMN_IS_FAVORITE + DB.EQUALS + DB.TRUE;
        return new Select().from(Publication.class).where(condition).execute();
    }

    public static Event getSingleEvent(Event event) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + event.mServerId;

        return new Select().from(Event.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Event event) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + event.mServerId;

        return new Select().from(Event.class).where(condition).exists();

    }

    public static void deleteAll() {

    }

    @Override
    public String getFacebookContentTitle() {
        return mName;
    }

    @Override
    public String getFacebookContentDescription() {
        return mSponsorName;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getOtherShareText() {
        return mName + "\n\n" + mSponsorName + "\n\n" + mUrl;
    }

    @Override
    public int getServerId() {
        return mServerId;
    }

    @Override
    public void reportFlurryEvent() {

        Flurry.logEvent(Flurry.EVENT_CLICK);
    }
}