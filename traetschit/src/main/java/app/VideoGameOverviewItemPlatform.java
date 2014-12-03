package app;

import java.util.List;

public class VideoGameOverviewItemPlatform{
    private VideoGameOverviewItem videoGameOverviewItem;
    private List<Platform> platforms;

    public VideoGameOverviewItemPlatform(VideoGameOverviewItem pVideoGameOverviewItem, List<Platform> pPlatforms) {
        videoGameOverviewItem = pVideoGameOverviewItem;
        platforms = pPlatforms;
    }

    public VideoGameOverviewItemPlatform() {
    }

    public VideoGameOverviewItem getVideoGameOverviewItem() {
        return videoGameOverviewItem;
    }

    public void setVideoGameOverviewItem(VideoGameOverviewItem pVideoGameOverviewItem) {
        videoGameOverviewItem = pVideoGameOverviewItem;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> pPlatforms) {
        platforms = pPlatforms;
    }
}
