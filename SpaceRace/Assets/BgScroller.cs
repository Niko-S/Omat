using UnityEngine;

public class BgScroller : MonoBehaviour
{
    public float scrollSpeed;
    private Vector3 startPosX = new Vector3(17, 0);

    void Start()
    {
        transform.position = startPosX;
    }

    void Update()
    {
        ScrollBG();
    }

    void ScrollBG()
    {
        transform.position += Vector3.left * scrollSpeed;
        if (transform.position.x <= -16)
        {
            transform.position = startPosX;
        }
    }
}