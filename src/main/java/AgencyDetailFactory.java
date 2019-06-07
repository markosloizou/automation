public class AgencyDetailFactory {
    public AgencyDetails GetAgencyRequirments(Agency a)
    {
        switch (a){
            case SHUTTERSTOCK:
                return new ShutterstockDetails();
            case DREAMSTIME:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case ALAMY:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case DEPOSITPHOTOS:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case CANSTOCKPHOTOS:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case POND5:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case ADOBESTOCK:
                throw new IllegalArgumentException("Unsupported Agency");
                //break;
            case TEST:
                return new TestDetails();
            default:
                throw new IllegalArgumentException("Unknown Agency");
        }
        //throw new IllegalArgumentException("Unknown Agency");
    }
}
