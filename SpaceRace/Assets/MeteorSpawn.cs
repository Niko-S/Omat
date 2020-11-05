using UnityEngine;

public class MeteorSpawn : MonoBehaviour
{
    public float speed;
    public GameObject t;

    // Start is called before the first frame update
    void Start()
    {
        transform.position = new Vector3(10, Random.Range(-4, 4));
    }

    // Update is called once per frame
    void Update()
    {
        transform.position += Vector3.left * speed;
        transform.Rotate(0, 0, 3);

        if (transform.position.x <= -10)
        {
            transform.position = new Vector3(10, Random.Range(-4, 4));
        }
    }

    void OnTriggerEnter2D(Collider2D col)
    {
            t.SetActive(true);
            Destroy(GameObject.FindGameObjectWithTag("Player"));
    }

}
