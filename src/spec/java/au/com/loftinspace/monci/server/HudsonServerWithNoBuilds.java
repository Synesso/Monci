package au.com.loftinspace.monci.server;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class HudsonServerWithNoBuilds {

    @Subject CIServer hudson;

    @BeforeSpecification
    void setUp() {
        hudson = new HudsonServer("http://hudson/rssAll");
    }

    @Specification
    void hasNoBuilds() {
        expect.that(hudson.getBuilds()).isEmpty();
    }

    @Specification
    void hasAHomePage() {
        expect.that(hudson.getHomepage()).isEqualTo("http://hudsonbuild/");
    }
}
