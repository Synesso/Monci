package au.com.loftinspace.monci.server;

import au.com.loftinspace.monci.domain.Build;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class HudsonServerWithOneBuild {

    @Subject
    CIServer hudson;
    private Build build;

    @BeforeSpecification
    void setUp() {
        hudson = new HudsonServer("http://hudson/rssAll");
        build = new Build();
        hudson.addBuild(build);
    }

    @Specification
    void hasNoBuilds() {
        expect.that(hudson.getBuilds()).containsItem(build);
    }

    @Specification
    void hasAHomePage() {
        expect.that(hudson.getHomepage()).isEqualTo("http://hudsonbuild/");
    }

}
