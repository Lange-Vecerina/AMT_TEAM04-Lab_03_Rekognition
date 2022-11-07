/**using System.IO;
        using System.Threading.Tasks;
        using NUnit.Framework;
        using Ria2ApiEvaluationModel.BucketManager;

        namespace TestRiaApiEvaluationModel
        {
public class TestAwsBucketManagerImpl
{

        #region private attributes
    private AwsBucketManagerImpl bucketManager;
    private string domain;
    private string bucketName;
    private string bucketUrl;
    private string imageName;
    private string pathToTestFolder;
    private string fullPathToImage;
    private string prefixObjectDownloaded;
        #endregion private attributes

        [SetUp]
    public void Init()
    {
        this.pathToTestFolder = Directory.GetCurrentDirectory().Replace("bin\\Debug\\netcoreapp3.1", "testData");
        this.bucketName = "testbucket";
        this.domain = "aws.dev.actualit.info";
        this.bucketUrl = bucketName + "." + this.domain;
        this.imageName = "emiratesa380.jpg";
        this.fullPathToImage = pathToTestFolder + "\\" + imageName;
        this.prefixObjectDownloaded = "downloaded";
        this.bucketManager = new AwsBucketManagerImpl(this.bucketUrl);
    }

        [Test]
    public async Task CreateObject_CreateNewBucket_Success()
{
    //given
    Assert.IsFalse(await this.bucketManager.Exists(bucketUrl));

    //when
    await this.bucketManager.CreateObject(bucketUrl);

    //then
    Assert.IsTrue(await this.bucketManager.Exists(bucketUrl));
}

        [Test]
    public async Task CreateObject_CreateObjectWithExistingBucket_Success()
{
    //given
    string fileName = this.imageName;
    string objectUrl = this.bucketUrl + "/" + this.imageName;
    await this.bucketManager.CreateObject(this.bucketUrl);
    Assert.IsTrue(await this.bucketManager.Exists(this.bucketUrl));
    Assert.IsFalse(await this.bucketManager.Exists(objectUrl));

    //when
    await this.bucketManager.CreateObject(objectUrl, this.pathToTestFolder + "//" + fileName);

    //then
    Assert.IsTrue(await this.bucketManager.Exists(objectUrl));
}

        [Test]
    public async Task CreateObject_CreateObjectBucketNotExist_Success()
{
    //given
    string fileName = this.imageName;
    string objectUrl = this.bucketUrl + "/" + this.imageName;
    Assert.IsFalse(await this.bucketManager.Exists(this.bucketUrl));
    Assert.IsFalse(await this.bucketManager.Exists(objectUrl));

    //when
    await this.bucketManager.CreateObject(objectUrl, this.pathToTestFolder + "//" + fileName);

    //then
    Assert.IsTrue(await this.bucketManager.Exists(objectUrl));
}

        [Test]
    public async Task DownloadObject_NominalCase_Success()
{
    //given
    string objectUrl = bucketUrl + "//" + this.imageName;
    string destinationFullPath = this.pathToTestFolder + "//" + this.prefixObjectDownloaded + this.imageName;
    await this.bucketManager.CreateObject(objectUrl, this.pathToTestFolder + "//" + this.imageName);

    Assert.IsTrue(await this.bucketManager.Exists(bucketUrl));

    //when
    await this.bucketManager.DownloadObject(objectUrl, destinationFullPath);

    //then
    Assert.IsTrue(File.Exists(destinationFullPath));
}

        [Test]
    public async Task Exists_NominalCase_Success()
{
    //given
    Task t = this.bucketManager.CreateObject(this.bucketUrl);
    await t;
    bool actualResult;

    //when
    actualResult = await this.bucketManager.Exists(bucketUrl);

    //then
    Assert.IsTrue(actualResult);
}

        [Test]
    public async Task Exists_ObjectNotExistBucket_Success()
{
    //given
    string notExistingBucket = "notExistingBucket" + this.domain;
    bool actualResult;

    //when
    actualResult = await this.bucketManager.Exists(notExistingBucket);

    //then
    Assert.IsFalse(actualResult);
}

        [Test]
    public async Task Exists_ObjectNotExistFile_Success()
{
    //given
    await this.bucketManager.CreateObject(this.bucketUrl);
    string notExistingFile = bucketUrl + "//" + "notExistingFile.jpg";
    Assert.IsTrue(await this.bucketManager.Exists(bucketUrl));
    bool actualResult;

    //when
    actualResult = await this.bucketManager.Exists(notExistingFile);

    //then
    Assert.IsFalse(actualResult);
}

        [Test]
    public async Task RemoveObject_EmptyBucket_Success()
{
    //given
    await this.bucketManager.CreateObject(this.bucketUrl);
    Assert.IsTrue(await this.bucketManager.Exists(bucketUrl));

    //when
    await this.bucketManager.RemoveObject(this.bucketUrl);

    //then
    Assert.IsFalse(await this.bucketManager.Exists(bucketUrl));
}

        [Test]
    public async Task RemoveObject_NotEmptyBucket_Success()
{
    //given
    string fileName = this.imageName;
    string objectUrl = this.bucketUrl + "/" + this.imageName;
    await this.bucketManager.CreateObject(this.bucketUrl);
    await this.bucketManager.CreateObject(objectUrl, this.pathToTestFolder + "//" + fileName);

    Assert.IsTrue(await this.bucketManager.Exists(bucketUrl));
    Assert.IsTrue(await this.bucketManager.Exists(objectUrl));

    //when
    await this.bucketManager.RemoveObject(this.bucketUrl);

    //then
    Assert.IsFalse(await this.bucketManager.Exists(bucketUrl));
}

        [TearDown]
    public async Task Cleanup()
{
    string destinationFullPath = this.pathToTestFolder + "\\" + this.prefixObjectDownloaded + this.imageName;

    if (File.Exists(destinationFullPath))
    {
        File.Delete(destinationFullPath);
    }

    this.bucketManager = new AwsBucketManagerImpl(this.bucketUrl);
    if (await this.bucketManager.Exists(bucketUrl))
    {
        await this.bucketManager.RemoveObject(this.bucketUrl);
    }
}
}
}**/
