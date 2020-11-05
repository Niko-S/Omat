using UnityEngine;

public class Controls : MonoBehaviour
{
    float speed = 0.4f;

    // Start is called before the first frame update
    void Start()
    { 

    }

    // Update is called once per frame
    void Update()
    {
        Move();
    }

    void Move()
    {
        if (Input.GetKey(KeyCode.UpArrow) && transform.position.y < 4)
        {
            transform.position += Vector3.up * speed;
        }
        if (Input.GetKey(KeyCode.DownArrow) && transform.position.y > -4)
        {
            transform.position -= Vector3.up * speed;
        }
    }
}

